package com.a24i.jobinterview.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.a24i.jobinterview.JobInterviewConfig
import com.a24i.jobinterview.entity.ChangedMovie
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers

class MainFragmentViewModel : BaseFragmentViewModel() {

    private val mItemClickedLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private val mLastDays = ObservableField(3)

    init {
        mItemClickedLiveData.value = emptyList()
    }

    fun onTextViewClick() {
        Log.d("clicked", "clickedwoow")
        mItemClickedLiveData.value = null  // Evoke new event
    }


    fun getItemClickedLiveData(): LiveData<List<Movie>> {
        return mItemClickedLiveData
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        JobInterviewConfig.REPOSITORY.getChangedMovies("2018-09-10", "2018-09-16", 1).subscribe {
            for (changedMovie: ChangedMovie in it.take(5)) {
                JobInterviewConfig.REPOSITORY.getMovie(changedMovie.id).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    var newList: MutableList<Movie> = mutableListOf()
                    newList.addAll(mItemClickedLiveData.value!!)
                    newList.add(it)
                    mItemClickedLiveData.value = newList
                }
            }
        }

    }

}
