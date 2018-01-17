package com.mvelusce.recipebook.repositories

import com.mvelusce.recipebook.entities.Ingredient
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository : CrudRepository<Ingredient, Long> {

    fun findById(id: Long): Ingredient?
}