package com.smartdev.mytestapp.domain.usecase

import com.smartdev.mytestapp.data.models.Images
import com.smartdev.mytestapp.domain.repository.ImagesRepository
import retrofit2.Response
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repo: ImagesRepository) {
    suspend fun loadImages(page: Int): Response<Images> = repo.getImages(page)
}