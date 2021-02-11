package com.example.tvtraker.model

data class Korisnik(
    val _id: String,
    val email: String,
    val ime: String,
    val kor_ime: String,
    val lozinka: String,
    val prezime: String,
    val serije: List<Any>
)
