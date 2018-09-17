package com.a24i.jobinterview.api;

import com.a24i.jobinterview.JobInterviewConfig
import io.reactivex.Observable
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path
import retrofit2.http.Query;

interface JobInterviewApi {
    @GET("/3/movie/changes?api_key=${JobInterviewConfig.API_KEY}")
    fun getChangedMovies(@Query("start_date") startDate: String, @Query("end_date") endDate: String, @Query("page") page: Int): Deferred<ChangedMoviesApi>

    @GET("/3/movie/{movie_id}?api_key=${JobInterviewConfig.API_KEY}")
    fun getMovie(@Path("movie_id") id: Long): Deferred<MovieApi>
}