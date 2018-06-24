package com.mvelusce.recipebook.http

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

interface HttpClient<out T : Any> {

    fun get(url: String, parameters: List<Pair<String, Any?>>? = null): Triple<Request, Response, Result<T, FuelError>>
}