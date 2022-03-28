package com.smartdev.mytestapp.presentation.images_list

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.smartdev.mytestapp.data.models.*
import com.smartdev.mytestapp.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val getImagesUseCase: GetImagesUseCase,
) : ViewModel() {

    // Хотел использовать LiveData, но для работы с paging library
    // подошло другое решение.
    private val _images = MutableLiveData<Result<Images>>()
    val images: LiveData<Result<Images>> = _images

    val imagesPagingSource by lazy {
        Pager(
            PagingConfig(pageSize = 20, initialLoadSize = 20),
            initialKey = savedState["lastPage"],
            pagingSourceFactory = {
                ImagesPagingSource(this)
            }
        )
            .flow
            .cachedIn(viewModelScope)
    }

    suspend fun loadImages(page: Int): Response<Images> {
        savedState["lastPage"] = page

        return getImagesUseCase.loadImages(page)
    }
}