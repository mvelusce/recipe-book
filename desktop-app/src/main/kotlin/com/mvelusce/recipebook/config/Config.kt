package com.mvelusce.recipebook.config

object Config {

    val webAppUrl = "http://localhost:9000/recipe-book/"

    val webAppExecCommand = "java -jar ./lib/recipe-book-web-app.war"
}

object WebAppStatusCheckerConfig {

    val attempts: Int = 50

    val retryMillis: Long = 2000
}