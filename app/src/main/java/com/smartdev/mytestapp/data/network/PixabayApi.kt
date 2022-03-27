package com.smartdev.mytestapp.data.network

import com.smartdev.mytestapp.data.models.Images
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET(".")
    suspend fun loadImages(@Query("page") page: Int): Response<Images>

    @GET(".")
    suspend fun searchImages(@Query("q") query: String, @Query("page") page: Int): Response<Images>

}