package com.a24i.jobinterview.entity

import android.os.Parcelable
import com.a24i.jobinterview.api.ProductionCompanyApi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCompany(
        val id: Int,
        val logo_path: String?,
        val name: String,
        val origin_country: String
): Parcelable {
    constructor(productionCompanyApi: ProductionCompanyApi): this(
            productionCompanyApi.id,
            productionCompanyApi.logo_path,
            productionCompanyApi.name,
            productionCompanyApi.origin_country
    )
}