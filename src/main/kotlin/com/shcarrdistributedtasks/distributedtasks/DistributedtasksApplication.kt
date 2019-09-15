package com.shcarrdistributedtasks.distributedtasks

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
class DistributedtasksApplication

fun main(args: Array<String>) {
    runApplication<DistributedtasksApplication>(*args)
}
