package com.mvelusce.recipebook.entities

import javax.persistence.*

@Entity
@Table(name = "ingredients")
class Ingredient {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    var name: String = ""
    var description: String = ""
}