package com.mvelusce.recipebook.loading

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory


class LoadWebAppTaskTest {

    private val logger = LoggerFactory.getLogger(LoadWebAppTaskTest::class.java)

    private val statusChecker: WebAppStatusChecker = WebAppStatusChecker()

    private val updater: (String) -> Unit = {logger.debug("Update message")}

    @Test
    fun callTestReturnSuccessWhenAppIsRunning() {

        val url = "http://www.google.com"

        val loadTask = LoadWebAppTask(statusChecker, 1, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.SUCCESS, result)
    }

    @Test
    fun callTestReturnFailureWhenAppIsNotRespondingAfterTimeout() {

        val url = "http://non.existing.url.com"

        val loadTask = LoadWebAppTask(statusChecker, 1, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.FAILED, result)
    }

    @Test
    fun callTestReturnSuccessAfterTwoAttempts() {

        val url = "http://www.google.com"

        val mockChecker = Mockito.mock(WebAppStatusChecker::class.java)
        Mockito.`when`(mockChecker.checkStatus(url))
                .thenReturn(LoadStatus.LOADING)
                .thenReturn(LoadStatus.SUCCESS)

        testOneAttempt(mockChecker, url)
        testTwoAttempts(mockChecker, url)
    }

    private fun testOneAttempt(mockChecker: WebAppStatusChecker, url: String) {

        val loadTask = LoadWebAppTask(mockChecker, 1, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.FAILED, result)
    }

    private fun testTwoAttempts(mockChecker: WebAppStatusChecker, url: String) {

        val loadTask = LoadWebAppTask(mockChecker, 2, 1)

        val result = loadTask.checkStatusRepeatedly(url, updater)

        Assert.assertEquals(LoadStatus.SUCCESS, result)
    }
}
