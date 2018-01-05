package com.mvelusce.recipebook.controllers

import com.mvelusce.recipebook.repositories.IngredientRepository
import com.mvelusce.recipebook.repositories.RecipeIngredientsRepository
import com.mvelusce.recipebook.repositories.RecipeRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["api/v1/data-management"])
class DataManagementController (
        val recipeRepo: RecipeRepository,
        val ingredientsRepo: IngredientRepository,
        val recipeIngredientsRepo: RecipeIngredientsRepository
) {
    // TODO field1 = name
    // field2 = type
    // from field3 to field24 ingredients
    // field4, field5, field6 ??
    // from field25 to field42 instructions
    // from field43 to field47 description
    // from field48 to ... notes

    // TODO import and export data
}