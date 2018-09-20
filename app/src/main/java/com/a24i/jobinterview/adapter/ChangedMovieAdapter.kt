package com.a24i.jobinterview.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.a24i.jobinterview.JobInterviewConfig

import com.a24i.jobinterview.databinding.FragmentMainItemBinding
import com.a24i.jobinterview.databinding.FragmentMainItemLoadingBinding
import com.a24i.jobinterview.entity.Movie
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch

class ChangedMovieAdapter(private val mListener: OnItemClickListener?) : RecyclerView.Adapter<ChangedMovieAdapter.ViewHolder>(), ProgressiveBindableAdapter<Movie> {
    val ITEM_VIEW_TYPE_MOVIE: Int = 0
    val ITEM_VIEW_TYPE_LOADER: Int = 1

    private lateinit var mRecyclerView: RecyclerView
    private var isLoading = false
    private var onLoadMoreListener: OnLoadMoreListener? = null

    override fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
    }

    override fun setData(data: List<Movie>) {
        clearData()
        addData(data)
    }

    override fun clearData() {
        isLoading = false
        var itemCount = mMovies.size
        mMovies.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    override fun addData(data: List<Movie>) {
        val positionStart = mMovies.size
        mMovies.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)

    }

    override fun showLoader() {
        val nullItemInList = mutableListOf<Movie?>()
        nullItemInList.add(null)
        mMovies.addAll(nullItemInList)
        notifyItemRangeInserted(mMovies.size - 1, 1)
    }

    override fun hideLoader() {
        if (isLoaderShown()) {
            mMovies.removeAt(mMovies.size - 1)
            notifyItemRemoved(mMovies.size)
        }
    }

    private fun isLoaderShown(): Boolean {
        return mMovies.size > 1 && mMovies[mMovies.size - 1] == null
    }

    private val mMovies: MutableList<Movie?> = mutableListOf()

    interface OnItemClickListener {
        fun onMovieSelected(movie: Movie)
    }

    override fun getItemViewType(position: Int): Int {
        return when (mMovies[position]) {
            null -> ITEM_VIEW_TYPE_LOADER
            else -> ITEM_VIEW_TYPE_MOVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = if (viewType == ITEM_VIEW_TYPE_MOVIE) {
            FragmentMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            FragmentMainItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movie = mMovies[position]
        when (holder.mBinding) {
            is FragmentMainItemBinding -> (holder.mBinding as FragmentMainItemBinding).apply {
                data = movie
                root.setOnClickListener {
                    movie?.let { mListener?.onMovieSelected(movie) }
                }
            }
            is FragmentMainItemLoadingBinding -> (holder.mBinding as FragmentMainItemLoadingBinding).apply {

            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        val gridLayoutManager = mRecyclerView.layoutManager as GridLayoutManager
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var totalItemCount = gridLayoutManager.itemCount
                var lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                if ( totalItemCount > JobInterviewConfig.LOAD_MORE_OFFSET
                        && (!isLoading && lastVisibleItem + JobInterviewConfig.LOAD_MORE_OFFSET >= totalItemCount) ) {
                    GlobalScope.launch(Dispatchers.Main) {
                        isLoading = true
                        onLoadMoreListener?.onLoadMore()
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    class ViewHolder(internal var mBinding: ViewDataBinding) : RecyclerView.ViewHolder(mBinding.root)
}
