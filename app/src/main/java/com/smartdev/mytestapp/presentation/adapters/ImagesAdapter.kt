package com.smartdev.mytestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartdev.mytestapp.App
import com.smartdev.mytestapp.data.models.Images
import com.smartdev.mytestapp.databinding.ImageItemBinding
import com.smartdev.mytestapp.presentation.util.GlideLoadStatus
import com.smartdev.mytestapp.presentation.util.loadImage
import com.smartdev.mytestapp.presentation.util.requestNewSize

class ImagesAdapter() :
    PagingDataAdapter<Images.Image, ImagesAdapter.ImageHolder>(ImageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] is ClipBounds.Clear) {
            holder.clearClipBounds()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var image: Images.Image? = null
        private var imageLoadAnyStatus = false
        private val glideLoadStatus = object : GlideLoadStatus {
            override fun imageLoadStatus(success: Boolean) {
                imageLoadAnyStatus = true
            }
        }

        init {

        }

        fun bind(mImage: Images.Image?) {
            imageLoadAnyStatus = false
            image = mImage
            binding.run {
                if (mImage != null) {
                    ViewCompat.setTransitionName(binding.root, mImage.largeImageURL)
                    val ratio = mImage.imageHeight.toFloat() / mImage.imageWidth.toFloat()
                    val height = ((App.screenWidth) * ratio).toInt()
                    root.requestNewSize(height = height)
                    image.requestNewSize(height = height)
                    image.loadImage(mImage.largeImageURL, glideLoadStatus)
                }
            }
        }

        fun clearClipBounds() {
            binding.root.clipBounds = null
        }
    }

    object ImageDiffCallback : DiffUtil.ItemCallback<Images.Image>() {
        override fun areItemsTheSame(oldItem: Images.Image, newItem: Images.Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Images.Image, newItem: Images.Image): Boolean {
            return oldItem == newItem
        }
    }

    sealed class ClipBounds {
        class Clear
    }
}