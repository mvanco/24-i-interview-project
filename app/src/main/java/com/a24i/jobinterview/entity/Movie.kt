package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.MovieApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val adult: Boolean = true,
        val backdrop_path: String? = null,
        val belongs_to_collection: Collection? = null,
        val budget: Int = 0,
        val genres: List<Genre> = emptyList(),
        val homepage: String? = null,
        val id: Int = 0,
        val imdb_id: String? = "",
        val original_language: String = "",
        val original_title: String = "",
        val overview: String? = null,
        val popularity: Double = 0.0,
        val poster_path: String? = null,
        val production_companies: List<ProductionCompany> = emptyList(),
        val production_countries: List<ProductionCountry> = emptyList(),
        val release_date: String = "",
        val revenue: Int = 0,
        val runtime: Int? = null,
        val spoken_languages: List<SpokenLanguage> = emptyList(),
        val status: String = "",
        val tagline: String? = null,
        val title: String = "",
        val video: Boolean = false,
        val vote_average: Double = 0.0,
        val vote_count: Int = 0
): Parcelable {
    constructor(movieApi: MovieApi): this(
            movieApi.adult,
            movieApi.backdrop_path,
            movieApi.belongs_to_collection?.let {Collection( movieApi.belongs_to_collection )},
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