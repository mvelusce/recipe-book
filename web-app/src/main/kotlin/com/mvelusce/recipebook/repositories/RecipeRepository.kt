package com.mvelusce.recipebook.repositories

import com.mvelusce.recipebook.entities.Recipe
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeRepository : CrudRepository<Recipe, Long> {

    override fun findById(id: Long): Optional<Recipe>
}