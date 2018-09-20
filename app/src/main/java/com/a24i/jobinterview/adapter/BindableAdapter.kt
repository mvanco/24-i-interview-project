package com.a24i.jobinterview.adapter

interface BindableAdapter<in T> {
    fun setData(data: List<T>)
    fun clearData()
}