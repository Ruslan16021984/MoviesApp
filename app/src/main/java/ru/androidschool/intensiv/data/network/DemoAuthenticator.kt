package ru.androidschool.intensiv.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class DemoAuthenticator(): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = ""
        return response.request.newBuilder().removeHeader("Auth-token")
            .addHeader("Auth-token", token)
            .build()
    }
}