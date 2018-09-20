package com.a24i.jobinterview.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.*
import android.view.View
import com.a24i.jobinterview.JobInterviewConfig
import com.a24i.jobinterview.JobInterviewConfig.LAST_DAYS_DEFAULT
import com.a24i.jobinterview.JobInterviewConfig.SERVER_ATTEMPTS
import com.a24i.jobinterview.adapter.OnLoadMoreListener
import com.a24i.jobinterview.adapter.ProgressiveBindableAdapter
import com.a24i.jobinterview.api.ChangedMoviesApi
import com.a24i.jobinterview.api.MovieApi
import com.a24i.jobinterview.entity.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import java.text.SimpleDateFormat
import java.util.*

class MainFragmentViewModel : BaseFragmentViewModel(), OnLoadMoreListener {
    val mOnMovieListChanged: MutableLiveData<List<Movie>> = MutableLiveData()
    val mLastDays = ObservableField(LAST_DAYS_DEFAULT)
    val mLoaderShown = ObservableInt(View.INVISIBLE)
    val mBigLoaderShown = ObservableInt(View.INVISIBLE)
    private lateinit  var mBindableAdapter: ProgressiveBindableAdapter<Movie>
    private var reloadMoviesJob: Job? = null
    private var changedMovies: ChangedMoviesApi? = null
    private var mStartInd: Int = 0
    private var mPage = 1

    init {
        mOnMovieListChanged.value = emptyList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (changedMovies == null) {
            startNewLoading()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        reloadMoviesJob?.cancel()
    }

    override fun onLoadMore() {
        startLoading()
    }

    fun onLastDaysChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            return
        }
        mLastDays.set(s.toString())
        startNewLoading()
    }

    fun onPreviousPageClicked() {
        if (mPage > 1) {
            mPage--
        }
        startNewLoading()
    }

    fun onNextPageClicked() {
        changedMovies?.let {
            if (mPage < changedMovies!!.total_pages) {
                mPage++
            }
            startNewLoading()
        }
    }

    /**
     * Initialize two-way communication between ViewModel and Adapter
     */
    fun setupBindableAdapter(adapter: ProgressiveBindableAdapter<Movie>) {
        mBindableAdapter = adapter
        mBindableAdapter.setOnLoadMoreListener(this)
    }

    private fun startNewLoading() {
        mStartInd = 0
        changedMovies = null

        startLoading()
    }

    private fun startLoading() {
        if (isLoading()) {
            return
        }

        showLoader()
        mBindableAdapter.showLoader()

        GlobalScope.launch {
            var endInd = if (mStartInd + JobInterviewConfig.SIMULTANEOUS_REQUEST_COUNT > 100) 100 else mStartInd + JobInterviewConfig.SIMULTANEOUS_REQUEST_COUNT
            reloadMoviesJob = GlobalScope.async(Dispatchers.IO, CoroutineStart.DEFAULT, null, {

                if (changedMovies == null) {
                    mBindableAdapter.clearData()
                    for (i in 1..SERVER_ATTEMPTS) {  // Try 3 times.
                        val changedMoviesDef: Deferred<ChangedMoviesApi> = JobInterviewConfig.REPOSITORY.getChangedMovies(getStartDay(), getEndDay(), mPage)
                        try {
                            changedMovies = changedMoviesDef.await()
                        } catch (e: Exception) {
                        } finally {
                            if (changedMovies != null) {
                                break
                            }
                        }
                    }
                }

                if (changedMovies == null) {
                    return@async  // Shoud invoke Toast message that sever is unavailable.
                }

                val compToInd = if (endInd == -1) changedMovies!!.results.size else endInd
                val results = changedMovies!!.results.subList(mStartInd, compToInd)
                val resultList = results.map {
                    GlobalScope.async(Dispatchers.IO) {
                        var deferred: Deferred<MovieApi>? = null
                        attempts@ for (i in 1..SERVER_ATTEMPTS) {
                            deferred = JobInterviewConfig.REPOSITORY.getMovie(it.id)
                            while (deferred.isActive) {
                                delay(10)
                            }
                            if (!deferred?.isCompletedExceptionally) {
                                break@attempts
                            }
                            delay(2000)
                        }
                        deferred
                    }
                }

                while (!resultList.all { !it.isActive }) {
                    delay(250)
                }

                val movies = resultList.filter { !it.isCompletedExceptionally }

                .map {
                    it.getCompleted()
                }

                .filter {
                    !(it == null || it.isCompletedExceptionally)
                }

                .map {
                    it?.getCompleted()
                }

                .filter {
                    it != null
                }

                .map {
                    Movie().copy(
                            overview = it!!.overview,
                            poster_path = it!!.poster_path,
                            title = it!!.title
                    )
                }

                GlobalScope.async(Dispatchers.Main) {
                    hideLoader()
                    mBindableAdapter.hideLoader()
                    mBindableAdapter?.addData(movies)
                    mStartInd = endInd
                }
            })
        }
    }

    private fun showLoader() {
        if (changedMovies == null) {
            mBigLoaderShown.set(View.VISIBLE)
            mLoaderShown.set(View.INVISIBLE)
        }
        else {
            mBigLoaderShown.set(View.INVISIBLE)
            mLoaderShown.set(View.VISIBLE)
        }
    }

    private fun hideLoader() {
        mBigLoaderShown.set(View.INVISIBLE)
        mLoaderShown.set(View.INVISIBLE)
    }

    private fun isLoading(): Boolean {
        return (mBigLoaderShown.get() == View.VISIBLE) || (mLoaderShown.get() == View.VISIBLE)
    }

    private fun getStartDay(): String {
        var today = Date()
        val cal = Calendar.getInstance()
        cal.time = today
        cal.add(Calendar.DATE, -mLastDays.get()?.toInt()!!)
        val df = SimpleDateFormat(JobInterviewConfig.BASIC_DATE_FORMAT_SERVER)
        return df.format(cal.time)
    }

    private fun getEndDay(): String {
        var today = Date()
        val df = SimpleDateFormat(JobInterviewConfig.BASIC_DATE_FORMAT_SERVER)
        return df.format(today)
    }
}