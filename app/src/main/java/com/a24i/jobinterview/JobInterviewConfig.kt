package com.a24i.jobinterview

import com.a24i.jobinterview.repository.TheMovieDbRepository

object JobInterviewConfig {

    const val API_KEY: String = "f4645e0a351be5b34725446c326b3fb3"
    const val REST_BASE_URL: String = "https://api.themoviedb.org/"
    const val SERVER_TIMEOUT: Int = 3000
    const val BASIC_DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss"  // Used in PostgreSQL database and as basic string format for java.util.Date.
    val REPOSITORY = TheMovieDbRepository
}
