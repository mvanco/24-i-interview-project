package com.a24i.jobinterview.repository

import com.a24i.jobinterview.api.ChangedMoviesApi
import com.a24i.jobinterview.api.MovieApi
import kotlinx.coroutines.experimental.Deferred

interface Repository {

    fun getChangedMovies(startDate: String, endDate: String, page: Int): Deferred<ChangedMoviesApi>
    fun getMovie(id: Long): Deferred<MovieApi>

}