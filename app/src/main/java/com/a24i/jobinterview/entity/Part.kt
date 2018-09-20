package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.PartApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Part(
        val id: Int,
        val video: Boolean,
        val vote_count: Int,
        val vote_average: Double,
        val title: String,
        val release_date: String,
        val original_language: String,
        val original_title: String,
        val genre_ids: List<Int>,
        val backdrop_path: String,
        val adult: Boolean,
        val overview: String,
        val poster_path: String,
        val popularity: Double
): Parcelable {
    constructor(partApi: PartApi): this(
        partApi.id,
        partApi.video,
        partApi.vote_count,
        partApi.vote_average,
        partApi.title,
        partApi.release_date,
        partApi.original_language,
        partApi.original_title,
        partApi.genre_ids,
        partApi.backdrop_path,
        partApi.adult,
        partApi.overview,
        partApi.poster_path,
        partApi.popularity
    )
}