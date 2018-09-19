package com.a24i.jobinterview.fragment

import android.app.PendingIntent.getActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.a24i.jobinterview.adapter.ChangedMovieAdapter
import com.a24i.jobinterview.databinding.FragmentMainBinding
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.viewmodel.MainFragmentViewModel

class MainFragment : BaseFragment() {

    private lateinit var mViewModel: MainFragmentViewModel
    private lateinit var mBinding: FragmentMainBinding
    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mAdapter: ChangedMovieAdapter

    interface OnFragmentInteractionListener: ChangedMovieAdapter.OnItemClickListener

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        lifecycle.addObserver(mViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = ChangedMovieAdapter(mListener)
        mBinding.movies.adapter = mAdapter
        mViewModel.mOnMovieListChanged.observe(this, Observer<List<Movie>> {
            mAdapter.setData(it ?: emptyList())
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        }
        else {
            throw RuntimeException("${context.toString()} must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }



}
