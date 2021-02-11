package com.example.tvtraker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Zanr(
    val __v: Int,
    val _id: String,
    val naziv: String
) : Parcelable