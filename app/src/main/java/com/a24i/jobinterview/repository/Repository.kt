package com.a24i.jobinterview.repository

import com.a24i.jobinterview.api.ChangedMoviesApi
import com.a24i.jobinterview.api.MovieApi
import com.a24i.jobinterview.entity.ChangedMovie
import com.a24i.jobinterview.entity.Movie
import io.reactivex.Observable
import kotlinx.coroutines.experimental.Deferred

interface Repository {

    fun getChangedMovies(startDate: String, endDate: String, page: Int): Deferred<ChangedMoviesApi>
    fun getMovie(id: Long): Deferred<MovieApi>

}