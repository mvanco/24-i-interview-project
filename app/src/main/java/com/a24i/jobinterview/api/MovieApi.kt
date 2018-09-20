package com.a24i.jobinterview.api

data class MovieApi(
        val adult: Boolean,
        val backdrop_path: String?,
        val belongs_to_collection: CollectionApi?,
        val budget: Int,
        val genres: List<GenreApi>,
        val homepage: String?,
        val id: Int,
        val imdb_id: String?,
        val original_language: String,
        val original_title: String,
        val overview: String?,
        val popularity: Double,
        val poster_path: String?,
        val production_companies: List<ProductionCompanyApi>,
        val production_countries: List<ProductionCountryApi>,
        val release_date: String,
        val revenue: Int,
        val runtime: Int?,
        val spoken_languages: List<SpokenLanguageApi>,
        val status: String,
        val tagline: String?,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
)