package com.matxowy.dogfacts.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://dog-facts-api.herokuapp.com/api/v1/resources/dogs?number=30

interface DogFactsApiService {

    @GET("dogs")
    fun getFactsAsync(
        @Query("number") number: Int = 30
    ): Deferred<List<DogFactItem>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): DogFactsApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://dog-facts-api.herokuapp.com/api/v1/resources/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DogFactsApiService::class.java)
        }
    }
}