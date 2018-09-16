package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.SpokenLanguageApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpokenLanguage(
        val iso_639_1: String,
        val name: String
): Parcelable {
    constructor(spokenLanguageApi: SpokenLanguageApi): this(
            spokenLanguageApi.iso_639_1,
            spokenLanguageApi.name
    )
}