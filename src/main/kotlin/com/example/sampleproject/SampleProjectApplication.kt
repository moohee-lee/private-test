package com.example.sampleproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleProjectApplication

fun main(args: Array<String>) {
    runApplication<SampleProjectApplication>(*args)
}
