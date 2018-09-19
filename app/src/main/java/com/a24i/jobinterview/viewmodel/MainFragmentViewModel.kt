package com.a24i.jobinterview.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import android.util.Log
import com.a24i.jobinterview.JobInterviewConfig
import com.a24i.jobinterview.api.ChangedMoviesApi
import com.a24i.jobinterview.api.MovieApi
import com.a24i.jobinterview.entity.Movie
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainFragmentViewModel : BaseFragmentViewModel() {

    val mOnMovieListChanged: MutableLiveData<List<Movie>> = MutableLiveData()
    val mLastDays = ObservableField("3")
    private var reloadMoviesJob: Job? = null
    private var changedMovies: ChangedMoviesApi? = null
    private var mStartInd: Int = 0

    companion object {
        private const val REQUEST_CONT = 30  // How many requests are allowed in small interval.
    }

    init {
        mOnMovieListChanged.value = emptyList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreate() {
        GlobalScope.launch {
            loadNextMovies()
            while (!allMoviesLoaded()) {
                delay(15000)
                loadNextMovies()
            }
        }
    }

    private fun loadNextMovies() {
        var endInd = if (mStartInd + REQUEST_CONT > 100) 100 else mStartInd + REQUEST_CONT
        reloadMovies(getStartDay(), getEndDay(), 1, mStartInd, endInd)
        mStartInd = endInd
    }

    private fun allMoviesLoaded(): Boolean {
        return mStartInd == if (changedMovies == null) true else changedMovies!!.results.size
    }

    private fun clearChangedMovies() {
        changedMovies = null
    }

    private fun reloadMovies(startDate: String, endDate: String, page: Int, fromInd: Int = 0, toInd: Int = -1) {
        reloadMoviesJob = GlobalScope.async(Dispatchers.IO, CoroutineStart.DEFAULT, null, {

            if (changedMovies == null) {
                for (i in 1..3) {  // Try 3 times.
                    val changedMoviesDef: Deferred<ChangedMoviesApi> = JobInterviewConfig.REPOSITORY.getChangedMovies(startDate, endDate, page)
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

            val compToInd = if (toInd == -1) changedMovies!!.results.size else toInd
            val results = changedMovies!!.results.subList(fromInd, compToInd)
            val resultList = results.map {
                GlobalScope.async(Dispatchers.IO) {
                    var deferred: Deferred<MovieApi>? = null
                    attempts@ for (i in 1..1) {
                        Log.d("cor", "trying $i attempt with id ${it.id}")
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
                Log.d("cor", "still wating to finish from range ($fromInd-$toInd)")
                delay(10)
            }

            val movies = resultList.filter { !it.isCompletedExceptionally }.map {
                it.getCompleted()
            }.filter {
                !(it == null || it.isCompletedExceptionally)
            }.map {
                it?.getCompleted()
            }.filter {
                it != null
            }.map {
                Movie(it!!)
            }

            withContext(Dispatchers.Main) {
                Log.d("cor", "all should be ready now from range ($fromInd-$toInd)")
                if (toInd != -1) {
                    var newList = mutableListOf<Movie>()
                    newList.addAll(mOnMovieListChanged.value!!)
                    newList.addAll(movies)
                    mOnMovieListChanged.value = newList
                } else if ((mOnMovieListChanged.value == null)
                        || (mOnMovieListChanged.value != null && mOnMovieListChanged.value != movies)) {
                    mOnMovieListChanged.value = movies
                }
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        reloadMoviesJob?.cancel()
    }

    private fun getNumberOfMovies(): Int {
        return if (mOnMovieListChanged.value == null) {
            0
        } else {
            mOnMovieListChanged.value!!.size
        }
    }

    fun onLastDaysChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            return
        }
        mStartInd = 0
        mLastDays.set(s.toString())
        changedMovies = null
        onCreate()
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
