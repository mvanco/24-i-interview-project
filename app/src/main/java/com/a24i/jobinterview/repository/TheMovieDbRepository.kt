package com.a24i.jobinterview.repository

import com.a24i.jobinterview.api.ChangedMovieApi
import com.a24i.jobinterview.api.ChangedMoviesApi
import com.a24i.jobinterview.api.JobInterviewApi
import com.a24i.jobinterview.api.MovieApi
import com.a24i.jobinterview.entity.ChangedMovie
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.rest.JIClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.Deferred

object TheMovieDbRepository : Repository {

    private val mApiService = JIClient.createService(JobInterviewApi::class.java)

    override fun getChangedMovies(startDate: String, endDate: String, page: Int): Deferred<ChangedMoviesApi> {
        return mApiService.getChangedMovies(startDate, endDate, page)
    }

    override fun getMovie(id: Long): Deferred<MovieApi> {
        return mApiService.getMovie(id)
    }
}