package com.smartdev.mytestapp.presentation.util

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.color.MaterialColors
import com.smartdev.mytestapp.R

fun View.requestNewSize(
    width: Int? = null, height: Int? = null
) {
    if (width != null) {
        layoutParams.width = width
    }
    if (height != null) {
        layoutParams.height = height
    }
    layoutParams = layoutParams
}

fun View.applyMargin(
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(start ?: marginLeft, top ?: topMargin, end ?: marginRight, bottom ?: bottomMargin)
        }
    }
}


@BindingAdapter("loadImage", "loadListener", requireAll = false)
fun ImageView.loadImage(imageUri: String, loadListener: GlideLoadStatus? = null) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .applyDefaultRequestOptions(
            RequestOptions
                .centerCropTransform()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_image_load_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .load(imageUri)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadListener?.imageLoadStatus(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadListener?.imageLoadStatus(true)
                return false
            }
        })
        .into(this)
}

@BindingAdapter("invisibleIf")
fun View.invisibleIf(boolean: Boolean) {
    visibility = if (boolean) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("visibleIf")
fun View.visibleIf(boolean: Boolean) {
    visibility = if (boolean) View.VISIBLE else View.GONE
}

@BindingAdapter("goneIf")
fun View.goneIf(boolean: Boolean) {
    visibility = if (boolean) View.GONE else View.VISIBLE
}