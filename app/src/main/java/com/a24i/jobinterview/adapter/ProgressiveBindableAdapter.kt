package com.a24i.jobinterview.adapter

interface ProgressiveBindableAdapter<in T>: BindableAdapter<T> {
    fun addData(data: List<T>)
    fun showLoader()
    fun hideLoader()
    fun setOnLoadMoreListener(listener: OnLoadMoreListener)
}