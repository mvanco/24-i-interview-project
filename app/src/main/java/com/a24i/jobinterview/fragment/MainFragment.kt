package com.a24i.jobinterview.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

import com.a24i.jobinterview.adapter.ChangedMovieAdapter
import com.a24i.jobinterview.databinding.FragmentMainBinding
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

        mBinding.edLastDays.apply {
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus()
                    val imm = v.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    true
                }
                false
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    (v as TextView).text = ""
                }
            }
        }

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = ChangedMovieAdapter(mListener)
        mBinding.recycler.adapter = mAdapter
        mViewModel.setupBindableAdapter(mAdapter)
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