package com.mvelusce.recipebook

import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.Node
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.web.WebView

class Browser : Region() {

    val browser = WebView()
    val webEngine = browser.engine

    init {
        //apply the styles
        styleClass.add("browser")
        // load the web page
        webEngine.load("http://localhost:9000/recipe-book/")
        //add the web view to the scene
        children.add(browser)

    }

    private fun createSpacer(): Node {
        val spacer = Region()
        HBox.setHgrow(spacer, Priority.ALWAYS)
        return spacer
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