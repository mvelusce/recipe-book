package com.mvelusce.recipebook

import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.layout.Region
import javafx.scene.web.WebView

class Browser : Region() {

    private val webAppUrl = "http://localhost:9000/recipe-book/"

    private val browser = WebView()
    private val webEngine = browser.engine

    init {
        styleClass.add("browser")
        webEngine.load(webAppUrl)
        children.add(browser)

    }

    override fun layoutChildren() {
        val w = width
        val h = height
        layoutInArea(browser, 0.0, 0.0, w, h, 0.0, HPos.CENTER, VPos.CENTER)
    }

    override fun computePrefWidth(height: Double): Double {
        return 750.0
    }

    override fun computePrefHeight(width: Double): Double {
        return 500.0
    }
}