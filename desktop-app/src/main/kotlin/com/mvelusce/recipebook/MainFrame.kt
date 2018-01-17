package com.mvelusce.recipebook


import javafx.animation.FadeTransition
import javafx.application.Application
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.concurrent.Worker
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.ProgressBar
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration


class MainFrame : Application() {

    private val command = "java -jar ./webapp/web-app-0.0.1-SNAPSHOT.war"

    private lateinit var p: Process


    val APPLICATION_ICON = "http://cdn1.iconfinder.com/data/icons/Copenhagen/PNG/32/people.png"
    val SPLASH_IMAGE = "http://fxexperience.com/wp-content/uploads/2010/06/logo.png"

    private lateinit var splashLayout: Pane
    private lateinit var loadProgress: ProgressBar
    private lateinit var progressText: Label
    private lateinit var mainStage: Stage
    private val SPLASH_WIDTH = 676.0
    private val SPLASH_HEIGHT = 227.0
    

    override fun init() {
        val splash = ImageView(Image(SPLASH_IMAGE))
        loadProgress = ProgressBar()
        loadProgress.prefWidth = SPLASH_WIDTH - 20.0
        progressText = Label("Will find friends for peanuts . . .")
        splashLayout = VBox()
        splashLayout.children.addAll(splash, loadProgress, progressText)
        progressText.alignment = Pos.CENTER
        splashLayout.style = "-fx-padding: 5; " +
                "-fx-background-color: cornsilk; " +
                "-fx-border-width:5; " +
                "-fx-border-color: " +
                "linear-gradient(" +
                "to bottom, " +
                "chocolate, " +
                "derive(chocolate, 50%)" +
                ");"
        splashLayout.effect = DropShadow()
    }

    override fun start(stage: Stage) {


        val friendTask: Task<ObservableList<String>> = object : Task<ObservableList<String>>() {
            @Throws(InterruptedException::class)
            override fun call(): ObservableList<String> {
                val foundFriends = FXCollections.observableArrayList<String>()
                val availableFriends = FXCollections.observableArrayList(
                        "Fili", "Kili", "Oin", "Gloin", "Thorin",
                        "Dwalin", "Balin", "Bifur", "Bofur",
                        "Bombur", "Dori", "Nori", "Ori"
                )

                updateMessage("Finding friends . . .")
                for (i in availableFriends.indices) {
                    Thread.sleep(400)
                    updateProgress((i + 1).toLong(), availableFriends.size.toLong())
                    val nextFriend = availableFriends[i]
                    foundFriends.add(nextFriend)
                    updateMessage("Finding friends . . . found " + nextFriend)
                }
                Thread.sleep(400)
                updateMessage("All friends found.")

                return foundFriends
            }
        }

        showSplash(stage, friendTask, object : InitCompletionHandler {
            override fun complete() {
                showMainStage(friendTask.valueProperty())
            }
        })
        Thread(friendTask).start()


        /*startWebapp()

        Thread.sleep(20000)*/

        /*stage.title = "Desktop Application"
        stage.scene = Scene(Browser(), 750.0, 500.0, Color.web("#666970"))
        stage.show()*/
    }

    private fun showMainStage(friends: ReadOnlyObjectProperty<ObservableList<String>>) {
        mainStage = Stage(StageStyle.DECORATED)
        mainStage.title = "My Friends"
        mainStage.icons.add(Image(APPLICATION_ICON))

        val peopleView = ListView<String>()
        peopleView.itemsProperty().bind(friends)

        mainStage.scene = Scene(peopleView)
        mainStage.show()
    }

    private fun showSplash(initStage: Stage, task: Task<*>, initCompletionHandler: InitCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty())
        loadProgress.progressProperty().bind(task.progressProperty())

        task.stateProperty().addListener({ _, _, newState ->
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind()
                loadProgress.progress = 1.0
                initStage.toFront()
                val fadeSplash = FadeTransition(Duration.seconds(1.2), splashLayout)
                fadeSplash.fromValue = 1.0
                fadeSplash.toValue = 0.0
                fadeSplash.setOnFinished { initStage.hide() }
                fadeSplash.play()

                initCompletionHandler.complete()
            }
        })

        val splashScene = Scene(splashLayout, Color.TRANSPARENT)
        val bounds = Screen.getPrimary().bounds
        initStage.scene = splashScene
        initStage.x = bounds.minX + bounds.width / 2 - SPLASH_WIDTH / 2
        initStage.y = bounds.minY + bounds.height / 2 - SPLASH_HEIGHT / 2
        initStage.initStyle(StageStyle.TRANSPARENT)
        initStage.isAlwaysOnTop = true
        initStage.show()
    }

    override fun stop() {
        /*if (p.isAlive) {
            p.destroy()
        }*/
    }

    private fun startWebapp() {
        try {
            p = Runtime.getRuntime().exec(command)
        } catch (err: Exception) {
            err.printStackTrace()
        }

    }
}


interface InitCompletionHandler {
    fun complete()
}


fun main(args: Array<String>) {
    Application.launch(MainFrame::class.java, *args)
}