package com.a24i.jobinterview.repository

import com.a24i.jobinterview.api.ChangedMoviesApi
import com.a24i.jobinterview.api.JobInterviewApi
import com.a24i.jobinterview.api.MovieApi
import kotlinx.coroutines.experimental.Deferred

object TheMovieDbRepository : Repository {

    private val mApiService = JIRetrofitClient.createService(JobInterviewApi::class.java)

    override fun getChangedMovies(startDate: String, endDate: String, page: Int): Deferred<ChangedMoviesApi> {
        return mApiService.getChangedMovies(startDate, endDate, page)
    }

    override fun getMovie(id: Long): Deferred<MovieApi> {
        return mApiService.getMovie(id)
    }
}