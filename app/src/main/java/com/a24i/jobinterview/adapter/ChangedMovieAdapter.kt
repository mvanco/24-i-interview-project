package com.a24i.jobinterview.adapter

import android.support.v7.widget.RecyclerView
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import com.a24i.jobinterview.adapter.ChangedMovieAdapter.ViewHolder

import com.a24i.jobinterview.databinding.FragmentMainItemBinding
import com.a24i.jobinterview.entity.Movie

import java.util.ArrayList

class ChangedMovieAdapter(private val mListener: OnItemClickListener?) : RecyclerView.Adapter<ChangedMovieAdapter.ViewHolder>() {

    private val mMovies: MutableList<Movie> = mutableListOf<Movie>()

    interface OnItemClickListener {
        fun onMovieSelected(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movie = mMovies[position]
        holder.mBinding.data = movie
        holder.mBinding.root.setOnClickListener {
            mListener?.onMovieSelected(movie)
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    open fun setData(movies: List<Movie>) {
        mMovies.clear()
        mMovies.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(internal var mBinding: FragmentMainItemBinding) : RecyclerView.ViewHolder(mBinding.root)
}
