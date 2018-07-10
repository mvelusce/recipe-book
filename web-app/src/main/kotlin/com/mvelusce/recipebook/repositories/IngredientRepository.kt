package com.mvelusce.recipebook.repositories

import com.mvelusce.recipebook.entities.Ingredient
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredientRepository : CrudRepository<Ingredient, Long> {

    override fun findById(id: Long): Optional<Ingredient>
}