package com.mvelusce.recipebook.loading

import com.github.kittinunf.result.Result
import com.mvelusce.recipebook.http.FuelHttpClient
import com.mvelusce.recipebook.http.HttpClient
import org.slf4j.LoggerFactory


class WebAppStatusChecker(private val httpClient: HttpClient<String> = FuelHttpClient()) {

    private val logger = LoggerFactory.getLogger(WebAppStatusChecker::class.java)

    fun checkStatus(url: String): LoadStatus {
        logger.info("Check status on $url")
        val (_, _, result) = httpClient.get(url)

        return when (result) {
            is Result.Failure -> {
                logger.info("Request returned error code. Returning loading status.")
                LoadStatus.LOADING
            }
            is Result.Success -> {
                logger.info("Request returned success code. Returning success status.")
                LoadStatus.SUCCESS
            }
            else -> {
                logger.info("Request returned unknown code: $result. Returning loading status.")
                LoadStatus.LOADING
            }
        }
    }
}