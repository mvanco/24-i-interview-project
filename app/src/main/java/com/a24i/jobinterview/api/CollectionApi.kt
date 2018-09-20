package com.a24i.jobinterview.api

data class CollectionApi(
        val id: Int,
        val name: String,
        val overview: String,
        val poster_path: String,
        val backdrop_path: String,
        val parts: List<PartApi>
)