package com.mvelusce.recipebook


import com.mvelusce.recipebook.browser.Browser
import com.mvelusce.recipebook.config.Config
import com.mvelusce.recipebook.loading.LoadStatus
import com.mvelusce.recipebook.loading.LoadWebAppTask
import com.mvelusce.recipebook.loading.WebAppStatusChecker
import javafx.animation.FadeTransition
import javafx.application.Application
import javafx.concurrent.Task
import javafx.concurrent.Worker
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
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

    private lateinit var webAppProcess: Process

    private val splashImage = "http://fxexperience.com/wp-content/uploads/2010/06/logo.png"

    private lateinit var splashLayout: Pane
    private lateinit var loadProgress: ProgressIndicator
    private lateinit var progressText: Label
    private lateinit var mainStage: Stage
    private val splashWidth = 676.0
    private val splashHeight = 227.0

    override fun init() {
        val splash = ImageView(Image(splashImage))
        loadProgress = ProgressIndicator()
        loadProgress.prefWidth = splashWidth - 20.0
        progressText = Label("Loading recipe book . . .")
        splashLayout = VBox()
        splashLayout.children.addAll(splash, loadProgress, progressText)
        progressText.alignment = Pos.CENTER
        splashLayout.style = "-fx-padding: 10; " +
                "-fx-background-color: white; " +
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

        val statusChecker = WebAppStatusChecker()

        val loadTask: Task<LoadStatus> = LoadWebAppTask(statusChecker, Config.webAppUrl, 50, 2000)

        showSplash(stage, loadTask, object : InitCompletionHandler {
            override fun complete() {
                showMainStage()
            }
        })
        Thread(loadTask).start()

        startWebApp()
    }

    private fun showMainStage() {

        mainStage = Stage(StageStyle.DECORATED)
        mainStage.title = "Desktop Application"
        mainStage.scene = Scene(Browser(), 750.0, 500.0, Color.web("#666970"))
        val bounds = Screen.getPrimary().bounds
        mainStage.x = bounds.minX + bounds.width / 2 - 750.0 / 2
        mainStage.y = bounds.minY + bounds.height / 2 - 500.0 / 2
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
        initStage.x = bounds.minX + bounds.width / 2 - splashWidth / 2
        initStage.y = bounds.minY + bounds.height / 2 - splashHeight / 2
        initStage.initStyle(StageStyle.TRANSPARENT)
        initStage.isAlwaysOnTop = true
        initStage.show()
    }

    override fun stop() {
        if (webAppProcess.isAlive) {
            webAppProcess.destroy()
        }
    }

    private fun startWebApp() {
        try {
            webAppProcess = Runtime.getRuntime().exec(Config.webAppExecCommand)
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
