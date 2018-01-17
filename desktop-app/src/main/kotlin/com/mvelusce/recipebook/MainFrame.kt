package com.mvelusce.recipebook

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage

class MainFrame : Application() {

    private var scene: Scene? = null

    override fun start(stage: Stage) {
        // create the scene
        stage.title = "Desktop Application"
        scene = Scene(Browser(), 750.0, 500.0, Color.web("#666970"))
        stage.scene = scene
        //scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show()
    }

    override fun stop() {
        if (p != null && p!!.isAlive) {
            p!!.destroy()
        }
    }

    companion object {

        private var p: Process? = null

        @Throws(InterruptedException::class)
        @JvmStatic
        fun main(args: Array<String>) {

            runWebapp()

            Thread.sleep(20000)

            launch(MainFrame::class.java, *args)
        }

        private fun runWebapp() {
            try {
                val command = "java -jar ./webapp/web-app-0.0.1-SNAPSHOT.war"

                val line: String
                p = Runtime.getRuntime().exec(command)

                /*BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();*/
            } catch (err: Exception) {
                err.printStackTrace()
            }

        }
    }
}