package com.mvelusce.recipebook.loading

import javafx.concurrent.Worker
import javafx.concurrent.WorkerStateEvent
import javafx.event.EventHandler
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CompletableFuture
import de.saxsys.javafx.test.JfxRunner
import org.mockito.Mockito


@RunWith(JfxRunner::class)
class LoadWebAppTaskTest {

    private val statusChecker: WebAppStatusChecker = WebAppStatusChecker()

    @Test
    fun callTestReturnSuccessWhenAppIsRunning() {

        val url = "http://www.google.com"
        val futureTestResults = CompletableFuture<TestResults>()

        val loadTask = setupTask(statusChecker, url, 1, futureTestResults)

        Thread(loadTask).start()

        val result = futureTestResults.get()

        Assert.assertEquals(LoadWebAppTask.successMessage, result.message)
        Assert.assertEquals(Worker.State.SUCCEEDED, result.state)
        Assert.assertEquals(LoadStatus.SUCCESS, result.loadStatus)
    }

    @Test
    fun callTestReturnFailureWhenAppIsNotRespondingAfterTimeout() {

        val url = "http://non.existing.url.com"
        val futureTestResults = CompletableFuture<TestResults>()

        val loadTask = setupTask(statusChecker, url, 1, futureTestResults)

        Thread(loadTask).start()

        val result = futureTestResults.get()

        Assert.assertEquals(LoadWebAppTask.failedMessage, result.message)
        Assert.assertEquals(Worker.State.SUCCEEDED, result.state)
        Assert.assertEquals(LoadStatus.FAILED, result.loadStatus)
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
        val futureTestResults = CompletableFuture<TestResults>()

        val loadTask = setupTask(mockChecker, url, 1, futureTestResults)

        Thread(loadTask).start()

        val result = futureTestResults.get()

        Assert.assertEquals(LoadWebAppTask.failedMessage, result.message)
        Assert.assertEquals(Worker.State.SUCCEEDED, result.state)
        Assert.assertEquals(LoadStatus.FAILED, result.loadStatus)
    }

    private fun testTwoAttempts(mockChecker: WebAppStatusChecker, url: String) {
        val futureTestResults = CompletableFuture<TestResults>()

        val loadTask = setupTask(mockChecker, url, 2, futureTestResults)

        Thread(loadTask).start()

        val result = futureTestResults.get()

        Assert.assertEquals(LoadWebAppTask.successMessage, result.message)
        Assert.assertEquals(Worker.State.SUCCEEDED, result.state)
        Assert.assertEquals(LoadStatus.SUCCESS, result.loadStatus)
    }

    private fun setupTask(checker: WebAppStatusChecker, url: String, attempts: Int,
                          futureTestResults: CompletableFuture<TestResults>): LoadWebAppTask {
        val loadTask = LoadWebAppTask(checker, url, attempts, 1)

        loadTask.onSucceeded = EventHandler<WorkerStateEvent> {
            futureTestResults.complete(TestResults(loadTask.message, loadTask.state, loadTask.value))
        }

        loadTask.onFailed = EventHandler<WorkerStateEvent> {
            futureTestResults.complete(TestResults(loadTask.message, loadTask.state, loadTask.value))
        }
        return loadTask
    }
}

private class TestResults (
        val message: String,
        val state: Worker.State,
        val loadStatus: LoadStatus
)
