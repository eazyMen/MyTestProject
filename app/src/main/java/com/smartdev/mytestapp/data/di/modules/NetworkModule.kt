package com.smartdev.mytestapp.data.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.smartdev.mytestapp.BuildConfig
import com.smartdev.mytestapp.data.network.PixabayApi
import com.smartdev.mytestapp.data.repository.ImagesRepositoryImpl
import com.smartdev.mytestapp.domain.repository.ImagesRepository
import com.smartdev.mytestapp.domain.usecase.GetImagesUseCase
import com.smartdev.mytestapp.presentation.images_list.ImagesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindImagesRepository(repo: ImagesRepositoryImpl): ImagesRepository

    companion object {
        @Provides
        @ViewModelScoped
        internal fun provideHomeApi(retrofit: Retrofit): PixabayApi {
            return retrofit.create(PixabayApi::class.java)
        }

        @Provides
        @ViewModelScoped
        internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @ViewModelScoped
        fun provideOkhttpClient(): OkHttpClient {
            val okHttpBuilder = OkHttpClient().newBuilder()
            okHttpBuilder.addInterceptor {
                val request = it.request()
                val url = request.url.newBuilder()
                    .addQueryParameter("key", BuildConfig.PIXABAY_API_KEY)
                    .build()
                it.proceed(request.newBuilder().url(url).build())
            }
            return okHttpBuilder.build()
        }
    }

}