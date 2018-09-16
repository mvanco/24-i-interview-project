package com.a24i.jobinterview.repository

import com.a24i.jobinterview.api.JobInterviewApi
import com.a24i.jobinterview.entity.ChangedMovie
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.rest.JIClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object TheMovieDbRepository : Repository {

    private val mApiService = JIClient.createService(JobInterviewApi::class.java)

    override fun getChangedMovies(startDate: String, endDate: String, page: Int): Observable<List<ChangedMovie>> {
        return mApiService.getChangedMovies(startDate, endDate, page).map { it.results.map { ChangedMovie(it) } }.subscribeOn(Schedulers.io())
    }

    override fun getMovie(id: Long): Observable<Movie> {
        return mApiService.getMovie(id).map { Movie(it) }.subscribeOn(Schedulers.io())
    }
}