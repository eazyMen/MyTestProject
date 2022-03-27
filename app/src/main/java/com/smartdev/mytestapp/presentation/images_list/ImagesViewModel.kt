package com.smartdev.mytestapp.presentation.images_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartdev.mytestapp.data.models.Images
import com.smartdev.mytestapp.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
) : ViewModel() {

    suspend fun loadImages(page: Int): Response<Images> {
        val response = getImagesUseCase.loadImages(page)
        viewModelScope.launch {
            if (response.isSuccessful && response.body() != null) {
                var a = 0
                var b = a
            }
        }
        return response
    }
}