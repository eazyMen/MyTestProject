package com.smartdev.mytestapp.domain.usecase

import com.smartdev.mytestapp.domain.repository.ImagesRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repo: ImagesRepository) {
    suspend fun loadImages(page: Int) = repo.getImages(page)
}