package com.example.tvtraker.webservice

import com.example.tvtraker.model.Korisnik
import com.example.tvtraker.model.MessageResponse
import com.example.tvtraker.model.TvSerija
import com.example.tvtraker.model.UsernameResponse
import retrofit2.http.*

interface SerijeApi {

    @GET("korisnici/login")
    suspend fun dohvatiKorisnika(
        @Query("kor_ime") kor_ime: String,
        @Query("lozinka") lozinka: String
    ): MessageResponse

    @POST("korisnici/registracija")
    suspend fun registerKorisnik(
        @Body korisnik: Korisnik
    ): MessageResponse

    @GET("serije/noveserije")
    suspend fun sveSerije(
        @Query("id") id:String
    ): List<TvSerija>

    @GET("serije/dodaneserije")
    suspend fun dodaneSerije(
        @Query("id") id:String
    ): List<TvSerija>

    @GET("serije/serijetrending")
    suspend fun trendingSerije(
        @Query("id") id:String
    ): List<TvSerija>

    @GET("serije/serijelatest")
    suspend fun latestSerije(
        @Query("id") id:String
    ): List<TvSerija>

    @GET("korisnici/dohvatiime")
    suspend fun dohvatiIme(
        @Query("id") id:String
    ): List<UsernameResponse>

    @GET("serije/dodajSerijuKorisniku")
    suspend fun dodajSeriju(
        @Query("serijaId") serijaId: String,
        @Query("korisnikId") korisnikId: String
    ) : MessageResponse

    @GET("serije/izbrisiserijukorisniku")
    suspend fun izbrisiSeriju(
        @Query("serijaId") serijaId: String,
        @Query("korisnikId") korisnikId: String
    ) : MessageResponse

}