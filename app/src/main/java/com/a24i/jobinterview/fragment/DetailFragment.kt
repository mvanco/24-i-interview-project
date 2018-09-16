package com.a24i.jobinterview.fragment

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.a24i.jobinterview.R
import com.a24i.jobinterview.databinding.FragmentDetailBinding
import com.a24i.jobinterview.viewmodel.DetailFragmentViewModel

class DetailFragment : BaseFragment() {

    private lateinit var mViewModel: DetailFragmentViewModel
    private lateinit var mBinding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DetailFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel
        return mBinding.root
    }
}
