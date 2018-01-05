package com.mvelusce.recipebook.integration

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getAllRecipes() {

        mockMvc
                .perform(get("/api/v1/recipes/"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(2)))
                .andExpect(jsonPath("$[0].id", `is`(1)))
                .andExpect(jsonPath("$[0].name", `is`("burrito")))
                .andExpect(jsonPath("$[1].id", `is`(2)))
                .andExpect(jsonPath("$[1].name", `is`("taco")))
    }

    @Test
    fun getRecipeById() {
        mockMvc
                .perform(get("/api/v1/recipes/1"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(1)))
                .andExpect(jsonPath("$.name", `is`("burrito")))
                .andExpect(jsonPath("$.description", `is`("mouth-watering mexican delight")))
                .andExpect(jsonPath("$.instructions", `is`("make burritos")))
                .andExpect(jsonPath("$.notes", `is`("it is delicious")))
    }
}