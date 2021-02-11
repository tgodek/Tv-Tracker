package com.example.tvtraker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TvSerija(
    val __v: Int,
    val _id: String,
    val glumci: String,
    val godina: Int,
    val naslov: String,
    val opis: String,
    val popularnost: Int,
    val poster: String,
    val zanr: List<Zanr>
) : Parcelable