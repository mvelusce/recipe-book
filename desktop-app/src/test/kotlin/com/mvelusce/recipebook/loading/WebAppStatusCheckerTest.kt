package com.mvelusce.recipebook.loading

import org.junit.Assert
import org.junit.Test


class WebAppStatusCheckerTest {

    private val statusChecker = WebAppStatusChecker()

    @Test
    fun checkStatusTestReturnSuccessWhenGetOfExistingUrl() {
        val existingUrl = "http://www.google.com"

        val res = statusChecker.checkStatus(existingUrl)

        Assert.assertEquals("Status checker should return success when the url exists", LoadStatus.SUCCESS, res)
    }

    @Test
    fun checkStatusTestReturnFailureWhenGetOfNonExistingUrl() {
        val nonExistingUrl = "http://non.existing.url.com"

        val res = statusChecker.checkStatus(nonExistingUrl)

        Assert.assertEquals("Status checker should return failure when the url does not exist", LoadStatus.LOADING, res)
    }
}