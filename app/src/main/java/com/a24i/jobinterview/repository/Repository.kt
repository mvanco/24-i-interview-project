package com.a24i.jobinterview.repository

import com.a24i.jobinterview.entity.ChangedMovie
import com.a24i.jobinterview.entity.Movie
import io.reactivex.Observable

interface Repository {

    fun getChangedMovies(startDate: String, endDate: String, page: Int): Observable<List<ChangedMovie>>
    fun getMovie(id: Long): Observable<Movie>

}