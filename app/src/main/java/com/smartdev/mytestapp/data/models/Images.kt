package com.smartdev.mytestapp.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable
import java.util.ArrayList


@Serializable
data class Images(
    val total: Int,
    val totalHits: Int,
    val hits: ArrayList<Image>?
)  {


    @Serializable
    data class Image(
        val id: Long,
        val tags: String?,
        val largeImageURL: String?,
        val imageWidth: Int,
        val imageHeight: Int,
        val views: Int,
        val downloads: Int,
        val likes: Int,
        val user_id: Int,
        val user: String?,
        val userImageURL: String?
    )
}