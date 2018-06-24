package com.mvelusce.recipebook.http

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class FuelHttpClient : HttpClient<String> {

    override fun get(url: String, parameters: List<Pair<String, Any?>>?):
            Triple<Request, Response, Result<String, FuelError>> =
        url.httpGet(parameters).responseString()
}