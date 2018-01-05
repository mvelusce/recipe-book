package com.mvelusce.recipebook.integration

import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeIngredientsIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getAllRecipeIngredients() {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/recipe-ingredients/"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipe", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ingredient", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity", Matchers.`is`("12oz")))
    }

    @Test
    fun getRecipeIngredientById() {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/recipe-ingredients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(1)))
    }

    @Test
    fun getIngredientByRecipe() {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/recipe-ingredients/recipe/1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipe", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ingredient", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity", Matchers.`is`("12oz")))
    }
}