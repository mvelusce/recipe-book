package com.mvelusce.recipebook.loading

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.mvelusce.recipebook.http.FuelHttpClient
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito


class WebAppStatusCheckerTest {

    private val mockRequest = Mockito.mock(Request::class.java)
    private val mockResponse = Mockito.mock(Response::class.java)
    private val mockHttpClient = Mockito.mock(FuelHttpClient::class.java)
    private val statusChecker = WebAppStatusChecker(mockHttpClient)

    @Test
    fun checkStatusTestReturnSuccessWhenGetOfExistingUrl() {
        val existingUrl = "http://www.google.com"

        Mockito.reset(mockHttpClient)
        Mockito.`when`(mockHttpClient.get(existingUrl))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Success("success")))

        val res = statusChecker.checkStatus(existingUrl)

        Assert.assertEquals("Status checker should return success when the url exists", LoadStatus.SUCCESS, res)
    }

    @Test
    fun checkStatusTestReturnFailureWhenGetOfNonExistingUrl() {
        val nonExistingUrl = "http://non.existing.url"

        Mockito.reset(mockHttpClient)
        Mockito.`when`(mockHttpClient.get(nonExistingUrl))
                .thenReturn(Triple(mockRequest, mockResponse, Result.Failure(FuelError(Exception("failure")))))

        val res = statusChecker.checkStatus(nonExistingUrl)

        Assert.assertEquals("Status checker should return failure when the url does not exist", LoadStatus.LOADING, res)
    }
}