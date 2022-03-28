package com.smartdev.mytestapp.presentation.images_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ConcatAdapter
import com.smartdev.mytestapp.R
import com.smartdev.mytestapp.data.models.*
import com.smartdev.mytestapp.databinding.ImagesFragmentBinding
import com.smartdev.mytestapp.presentation.adapters.ImagesAdapter
import com.smartdev.mytestapp.presentation.adapters.InitialLoadAdapter
import com.smartdev.mytestapp.presentation.adapters.LoadAdapter
import com.smartdev.mytestapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesFragment : BaseFragment<ImagesFragmentBinding>() {

    private val viewModel: ImagesViewModel by viewModels()
    private val adapter = ImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImagesFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // viewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.imagesPagingSource.collect {
                adapter.submitData(lifecycle, it)
            }
        }

        binding?.run {
            val header = LoadAdapter(adapter)
            val footer = LoadAdapter(adapter)
            val initialLoadAdapter = InitialLoadAdapter(3, R.layout.images_load_initial_items)

            imagesRV.adapter = ConcatAdapter(initialLoadAdapter, header, adapter, footer)
        }

    }
}

class ImagesPagingSource(private val vm: ImagesViewModel) : PagingSource<Int, Images.Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Images.Image> {
        val page = params.key ?: 1

        return when (val response = request { vm.loadImages(page) }) {
            is ApiResponse.Result<*> -> {
                val data = response.data as Images
                LoadResult.Page(
                    data.hits,
                    if (page > 1) page - 1 else null,
                    //Так и не нашел в апи кол-во доступных страниц, пришлось самому вычислять
                    if (page * 20 > data.totalHits) null else page + 1
                )
            }
            is ApiResponse.Error -> {
                LoadResult.Error(Throwable(response.desc))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Images.Image>): Int {

        return 1
    }
}
