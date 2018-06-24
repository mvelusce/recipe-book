package com.mvelusce.recipebook.loading

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.mvelusce.recipebook.http.FuelHttpClient
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory


class LoadWebAppTaskTest {

    private val logger = LoggerFactory.getLogger(LoadWebAppTaskTest::class.java)

    private val mockRequest = Mockito.mock(Request::class.java)
    private val mockResponse = Mockito.mock(Response::class.java)
    private val mockHttpClient = Mockito.mock(FuelHttpClient::class.java)
    private val statusChecker: WebAppStatusChecker = WebAppStatusChecker(mockHttpClient)

    private val updater: (String) -> Unit = {logger.debug("Update message")}

    @Test
    fun callTestReturnSuccessWhenAppIsRunning() {

        val url = "http://www.google.com"

        Mockito.reset(mockHttpClient)
        Mockito.`when`(mockHttpClient.get(url))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Success("success")))

        val loadTask = LoadWebAppTask(statusChecker, 1, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.SUCCESS, result)
    }

    @Test
    fun callTestReturnFailureWhenAppIsNotRespondingAfterTimeout() {

        val url = "http://non.existing.url"

        Mockito.reset(mockHttpClient)
        Mockito.`when`(mockHttpClient.get(url))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Failure(FuelError(Exception("failure")))))

        val loadTask = LoadWebAppTask(statusChecker, 1, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.FAILED, result)
    }

    @Test
    fun callTestReturnSuccessAfterTwoAttempts() {

        val url = "http://www.google.com"

        Mockito.reset(mockHttpClient)
        Mockito.`when`(mockHttpClient.get(url))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Failure(FuelError(Exception("failure")))))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Failure(FuelError(Exception("failure")))))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Success("success")))

        testOneAttempt(statusChecker, url)
        testTwoAttempts(statusChecker, url)
    }

    private fun testOneAttempt(statusChecker: WebAppStatusChecker, url: String) {

        val loadTask = LoadWebAppTask(statusChecker, 1, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.FAILED, result)
    }

    private fun testTwoAttempts(statusChecker: WebAppStatusChecker, url: String) {

        val loadTask = LoadWebAppTask(statusChecker, 2, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.SUCCESS, result)
    }
}
