package com.github.millgi.springintegration.it

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringIntegrationTestApplication

fun main(args: Array<String>) {
    runApplication<SpringIntegrationTestApplication>(*args)
}
