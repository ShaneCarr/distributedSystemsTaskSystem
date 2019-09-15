package net.`in`.dayan.springdemo.controller


import com.shcarrdistributedtasks.distributedtasks.entity.Todo
import com.shcarrdistributedtasks.distributedtasks.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import javax.servlet.http.HttpServletResponse
import java.io.File
import java.io.IOException

import java.util.Date

import java.nio.file.Files.*

@Controller
@RequestMapping(value = "/todo")
class TodoController {

    @Autowired
    internal var todoRepository: TodoRepository? = null

    @Autowired
    internal var env: Environment? = null

    @RequestMapping(value = "list", method = [RequestMethod.GET])
    fun list(model: Model): String {
        val list = todoRepository!!.findAll()
        for (todo in list) {
            if (getFile(todo.id) != null) {
                todo.setHasPic(true)
            }
        }
        model.addAttribute("todos", list)
        return "todo/list"
    }

    @RequestMapping(value = "edit/{id}", method = [RequestMethod.GET])
    fun edit(@PathVariable("id") id: Int?, model: Model): String {
        setStatusOptions(model)
        model.addAttribute("todo", todoRepository!!.findById(id).orElse(null))
        return "todo/edit"
    }

    @RequestMapping(value = "new", method = [RequestMethod.GET])
    fun create(model: Model): String {
        setStatusOptions(model)
        model.addAttribute("todo", Todo())
        return "todo/edit"
    }

    private fun setStatusOptions(model: Model) {
        model.addAttribute("statusOptions", Todo.statusOptions)
    }

    @RequestMapping(value = "save", method = [RequestMethod.POST])
    @Throws(IOException::class)
    fun save(@ModelAttribute("todo") todo: Todo, model: Model, response: HttpServletResponse): String? {
        if (todo.id != null) {
            val curTodo = todoRepository!!.findById(todo.id!!).orElse(null)
            todo.dt = curTodo.dt
        } else {
            todo.dt = Date()
        }
        todoRepository!!.save(todo)

        if (todo.pic != null) {
            val dir = env!!.getProperty("app.picDir")
            File(dir!!).mkdir()
            val ext = StringUtils.getFilenameExtension(todo!!.pic!!.originalFilename)
            todo!!.pic!!.transferTo(File(dir + "/" + todo.id + "." + ext))
        }

        response.sendRedirect("/todo/list")
        return null
    }

    @RequestMapping(value = "delete/{id}", method = [RequestMethod.GET])
    @Throws(IOException::class)
    fun delete(@PathVariable("id") id: Int?, model: Model, response: HttpServletResponse): String? {
        val todo = todoRepository!!.findById(id!!).orElse(null)
        if (todo != null) {
            todoRepository!!.delete(todo)
        }
        val file = getFile(id)
        file?.delete()

        response.sendRedirect("/todo/list")
        return null
    }

    @RequestMapping(value = "pic/{id}", method = [RequestMethod.GET])
    @Throws(IOException::class)
    fun pic(@PathVariable("id") id: Int?, model: Model): ResponseEntity<ByteArray>? {
        val file = getFile(id) ?: return null
        val image = readAllBytes(file.toPath())
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image)
    }

    private fun getFile(@PathVariable("id") id: Int?): File? {
        val dir = env!!.getProperty("app.picDir")
        var file = File("$dir/$id.png")
        if (!file.exists()) {
            file = File("$dir/$id.jpg")
        }
        return if (!file.exists()) {
            null
        } else file
    }
}
