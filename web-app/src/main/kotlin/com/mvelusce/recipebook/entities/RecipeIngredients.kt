package com.mvelusce.recipebook.entities

import javax.persistence.*

@Entity
@Table(name = "recipe_ingredients")
class RecipeIngredients {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    var recipe: Long = 0
    var ingredient: Long = 0
    var quantity: String = ""
}