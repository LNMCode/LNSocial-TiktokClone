package com.longnp.lnsocial.business.datasource.network

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    @SuppressLint("LongLogTag")
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url()).build()
        val bodyRequest = request.body()
        Log.d("Interception ", request.toString())
        Log.d("Interception body request: ", bodyRequest.toString())
        return chain.proceed(request)
    }
}