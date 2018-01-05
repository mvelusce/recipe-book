package com.mvelusce.recipebook.repositories

import com.mvelusce.recipebook.entities.Recipe
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : CrudRepository<Recipe, Long> {

    fun findById(id: Long): Recipe?
}