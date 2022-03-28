package com.smartdev.mytestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

class InitialLoadAdapter(private val num: Int, @LayoutRes private val layoutId: Int) :
    RecyclerView.Adapter<InitialLoadAdapter.LoadHolder>() {

    var loadState: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
        set(loadState) {
            if (field != loadState) {
                val oldItem = displayLoadStateAsItem(field)
                val newItem = displayLoadStateAsItem(loadState)

                if (oldItem && !newItem) {
                    notifyItemRangeRemoved(0, num)
                } else if (newItem && !oldItem) {
                    notifyItemRangeInserted(0, num)
                } else if (oldItem && newItem) {
                    notifyItemRangeChanged(0, num)
                }
                field = loadState
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadHolder {
        return LoadHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: LoadHolder, position: Int) {}

    override fun getItemViewType(position: Int): Int = getStateViewType(loadState)

    override fun getItemCount(): Int = if (displayLoadStateAsItem(loadState)) num else 0

    private fun getStateViewType(loadState: LoadState): Int = 0

    private fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }

    class LoadHolder(view: View) : RecyclerView.ViewHolder(view)
}