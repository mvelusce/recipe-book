package com.mvelusce.recipebook

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RecipeBookApplication

fun main(args: Array<String>) {
    SpringApplication.run(RecipeBookApplication::class.java, *args)
}
