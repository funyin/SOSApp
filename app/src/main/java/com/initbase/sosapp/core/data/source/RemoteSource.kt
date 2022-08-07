package com.initbase.sosapp.core.data.source

import com.initbase.sosapp.features.sos.data.source.remote.SosService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteSource {
    private const val BASE_URL = "http://dummy.restapiexample.com/api/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val sosService: SosService = getRetrofit().create(SosService::class.java)
}