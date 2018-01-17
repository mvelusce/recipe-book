package com.mvelusce.recipebook.controllers

import com.mvelusce.recipebook.repositories.RecipeIngredientsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["api/v1/recipe-ingredients"])
class RecipeIngredientsController (val repository: RecipeIngredientsRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = repository.findById(id)

    @GetMapping("/recipe/{recipe}")
    fun findByRecipe(@PathVariable recipe: Long) = repository.findRecipeIngredients(recipe)
}