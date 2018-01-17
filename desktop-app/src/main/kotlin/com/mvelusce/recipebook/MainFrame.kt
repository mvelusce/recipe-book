package com.mvelusce.recipebook

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage

class MainFrame : Application() {

    private val command = "java -jar ./webapp/web-app-0.0.1-SNAPSHOT.war"

    private var p: Process? = null

    override fun start(stage: Stage) {

        startWebapp()

        Thread.sleep(20000)

        stage.title = "Desktop Application"
        stage.scene = Scene(Browser(), 750.0, 500.0, Color.web("#666970"))
        stage.show()
    }

    override fun stop() {
        if (p?.isAlive == true) {
            p?.destroy()
        }
    }

    private fun startWebapp() {
        try {
            p = Runtime.getRuntime().exec(command)
        } catch (err: Exception) {
            err.printStackTrace()
        }

    }
}

fun main(args: Array<String>) {
    Application.launch(MainFrame::class.java, *args)
}