package com.smartdev.mytestapp.domain.repository

import com.smartdev.mytestapp.data.models.Images
import retrofit2.Response

interface ImagesRepository {
    suspend fun getImages(page: Int): Response<Images>
}