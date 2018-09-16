package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.ProductionCountryApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCountry(
        val iso_3166_1: String,
        val name: String
): Parcelable {
    constructor(productionCountryApi: ProductionCountryApi): this(
            productionCountryApi.iso_3166_1,
            productionCountryApi.name
    )
}