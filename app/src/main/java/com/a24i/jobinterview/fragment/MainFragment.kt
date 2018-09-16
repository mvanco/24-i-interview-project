package com.a24i.jobinterview.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.a24i.jobinterview.R
import com.a24i.jobinterview.adapter.ChangedMovieAdapter
import com.a24i.jobinterview.databinding.FragmentMainBinding
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.viewmodel.MainFragmentViewModel

class MainFragment : BaseFragment() {

    private lateinit var mViewModel: MainFragmentViewModel
    private lateinit var mBinding: FragmentMainBinding
    private var mListener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener: ChangedMovieAdapter.OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        mViewModel.getItemClickedLiveData().observe(this, Observer { Toast.makeText(activity, "jajko", Toast.LENGTH_LONG).show() })
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
        val layoutManager = LinearLayoutManager(activity)
        mBinding.movies.layoutManager = layoutManager
        val adapter = ChangedMovieAdapter(mListener)
        mBinding.movies.adapter = adapter
        mViewModel.getItemClickedLiveData().observe(this, Observer<List<Movie>> {
           adapter.setData(it ?: emptyList())
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
