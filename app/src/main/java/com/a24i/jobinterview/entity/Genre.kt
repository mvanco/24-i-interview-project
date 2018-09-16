package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.GenreApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
        val id: Int,
        val name: String
): Parcelable {
    constructor(genreApi: GenreApi): this(genreApi.id, genreApi.name)
}