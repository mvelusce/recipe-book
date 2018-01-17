package com.mvelusce.recipebook.entities

import javax.persistence.*

@Entity
@Table(name = "recipes")
class Recipe(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        var name: String = "",
        var type: String = "",
        var description: String = "",
        var instructions: String = "",
        var notes: String = ""
)
