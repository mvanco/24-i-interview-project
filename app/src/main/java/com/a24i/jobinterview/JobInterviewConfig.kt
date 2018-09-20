package com.a24i.jobinterview

import com.a24i.jobinterview.repository.TheMovieDbRepository

object JobInterviewConfig {

    const val API_KEY: String = "f4645e0a351be5b34725446c326b3fb3"
    const val REST_BASE_URL: String = "https://api.themoviedb.org/"
    const val MOVIE_DB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w400"
    const val SERVER_TIMEOUT: Int = 3000
    const val BASIC_DATE_FORMAT_SERVER = "yyyy-MM-dd"
    const val SIMULTANEOUS_REQUEST_COUNT = 10
    const val LOAD_MORE_OFFSET = 2


    val REPOSITORY = TheMovieDbRepository
}
