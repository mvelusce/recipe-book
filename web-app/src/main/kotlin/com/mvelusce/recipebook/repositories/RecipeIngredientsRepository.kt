package com.mvelusce.recipebook.repositories

import com.mvelusce.recipebook.entities.RecipeIngredients
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeIngredientsRepository : CrudRepository<RecipeIngredients, Long> {

    override fun findById(id: Long): Optional<RecipeIngredients>

    @Query("select ri from RecipeIngredients ri where ri.recipe = :recipe")
    fun findRecipeIngredients(@Param("recipe") recipe: Long): List<RecipeIngredients>?
}