package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.MovieApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val adult: Boolean,
        val backdrop_path: String?,
        val belongs_to_collection: Boolean?,
        val budget: Int,
        val genres: List<Genre>,
        val homepage: String?,
        val id: Int,
        val imdb_id: String?,
        val original_language: String,
        val original_title: String,
        val overview: String?,
        val popularity: Double,
        val poster_path: String?,
        val production_companies: List<ProductionCompany>,
        val production_countries: List<ProductionCountry>,
        val release_date: String,
        val revenue: Int,
        val runtime: Int?,
        val spoken_languages: List<SpokenLanguage>,
        val status: String,
        val tagline: String?,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
): Parcelable {
    constructor(movieApi: MovieApi): this(
            movieApi.adult,
            movieApi.backdrop_path,
            movieApi.belongs_to_collection,
            movieApi.budget,
            movieApi.genres.map { Genre(it) },
            movieApi.homepage,
            movieApi.id,
            movieApi.imdb_id,
            movieApi.original_language,
            movieApi.original_title,
            movieApi.overview,
            movieApi.popularity,
            movieApi.poster_path,
            movieApi.production_companies.map {ProductionCompany(it)},
            movieApi.production_countries.map {ProductionCountry(it)},
            movieApi.release_date,
            movieApi.revenue,
            movieApi.runtime,
            movieApi.spoken_languages.map {SpokenLanguage(it)},
            movieApi.status,
            movieApi.tagline,
            movieApi.title,
            movieApi.video,
            movieApi.vote_average,
            movieApi.vote_count
    )
}