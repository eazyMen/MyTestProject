package com.smartdev.mytestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartdev.mytestapp.databinding.ProgressCircularItemBinding
import com.smartdev.mytestapp.presentation.util.visibleIf

class LoadAdapter(private val imagesAdapter: ImagesAdapter) : LoadStateAdapter<LoadAdapter.ProgressHolderCircular>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): ProgressHolderCircular {
        return ProgressHolderCircular(
            ProgressCircularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProgressHolderCircular, loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> holder.bind(visibilityFlag = false, error = true, mErrorText = loadState.error.message ?: "Ошибка")
            else -> holder.bind(!loadState.endOfPaginationReached, false, mErrorText = "")
        }
    }

    inner class ProgressHolderCircular(private val view: ProgressCircularItemBinding) : RecyclerView.ViewHolder(view.root) {

        init {
            view.run {
                retryBtn.setOnClickListener {
                    imagesAdapter.retry()
                }
            }
        }

        fun bind(visibilityFlag: Boolean, error: Boolean, mErrorText: String) {
            view.run {
                errorCnt.visibleIf(error)
                progress.visibleIf(visibilityFlag && !error)
                errorText.text = mErrorText
            }
        }
    }
}