package com.example.tleapplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TleApplication

fun main(args: Array<String>) {
    runApplication<TleApplication>(*args)
}
