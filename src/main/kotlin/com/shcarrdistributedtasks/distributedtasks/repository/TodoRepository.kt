package com.shcarrdistributedtasks.distributedtasks.repository


import com.shcarrdistributedtasks.distributedtasks.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Int>
