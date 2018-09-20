package com.a24i.jobinterview.api

data class ChangedMoviesApi(
    var results: List<ChangedMovieApi>,
    var page: Int,
    var total_pages: Int,
    var total_results: Int
)