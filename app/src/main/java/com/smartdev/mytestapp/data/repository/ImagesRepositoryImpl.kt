package com.smartdev.mytestapp.data.repository

import com.smartdev.mytestapp.data.models.Images
import com.smartdev.mytestapp.data.network.PixabayApi
import com.smartdev.mytestapp.domain.repository.ImagesRepository
import retrofit2.Response
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(private val api: PixabayApi) : ImagesRepository{

    override suspend fun getImages(page: Int): Response<Images> {
        return api.loadImages(page)
    }

}