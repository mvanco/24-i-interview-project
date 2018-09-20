package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.CollectionApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
        val id: Int,
        val name: String,
        val overview: String,
        val poster_path: String,
        val backdrop_path: String,
        val parts: List<Part>
): Parcelable {
    constructor(collectionApi: CollectionApi): this(
            collectionApi.id,
            collectionApi.name,
            collectionApi.overview,
            collectionApi.poster_path,
            collectionApi.backdrop_path,
            collectionApi.parts.map { Part(it) }
    )
}