package com.mvelusce.recipebook.controllers

import com.mvelusce.recipebook.repositories.RecipeRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["api/v1/recipes"])
class RecipeController (val repository: RecipeRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = repository.findById(id)

    // TODO @PostMapping( value = "/insert" )
}