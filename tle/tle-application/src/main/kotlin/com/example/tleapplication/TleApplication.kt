package com.example.tleapplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TleApplication

fun main(args: Array<String>) {
    runApplication<TleApplication>(*args)
}
