package com.shcarrdistributedtasks.distributedtasks.entity

import org.springframework.web.multipart.MultipartFile

import javax.persistence.*
import java.util.Date

@Entity
@Table(name = "todos")
class Todo {

    @Id
    @SequenceGenerator(name = "todoSeq", sequenceName = "todo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoSeq")
    var id: Int? = null

    var title: String? = null

    var status: Int? = null

    var dt: Date? = null

    @Transient
    var pic: MultipartFile? = null

    @Transient
    private var hasPic = false

    val statusStr: String
        get() = if (status == null || status == 0) "Open" else "Close"

    fun setHasPic(b: Boolean) {
        this.hasPic = b
    }

    fun hasPic(): Boolean {
        return this.hasPic
    }

    class Option(var id: Int?, var name: String)

    companion object {

        var statusOptions = arrayOf(Option(0, "Open"), Option(1, "Close"))
    }
}
