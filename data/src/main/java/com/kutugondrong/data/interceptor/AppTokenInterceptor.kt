package com.kutugondrong.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AppTokenInterceptor(private val appToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "token $appToken")
                .build()
        )
    }
}