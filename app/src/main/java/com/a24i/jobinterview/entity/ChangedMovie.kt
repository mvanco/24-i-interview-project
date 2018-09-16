package com.a24i.jobinterview.entity

import com.a24i.jobinterview.api.ChangedMovieApi

data class ChangedMovie(
        var id: Long,
        var adult: Boolean
) {
    constructor(changedMovieApi: ChangedMovieApi): this(changedMovieApi.id, changedMovieApi.adult)
}