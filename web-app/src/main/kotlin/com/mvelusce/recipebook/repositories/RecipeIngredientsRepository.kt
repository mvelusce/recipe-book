package com.mvelusce.recipebook.repositories

import com.mvelusce.recipebook.entities.RecipeIngredients
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RecipeIngredientsRepository : CrudRepository<RecipeIngredients, Long> {

    fun findById(id: Long): RecipeIngredients?

    @Query("select ri from RecipeIngredients ri where ri.recipe = :recipe")
    fun findRecipeIngredients(@Param("recipe") recipe: Long): List<RecipeIngredients>?
}