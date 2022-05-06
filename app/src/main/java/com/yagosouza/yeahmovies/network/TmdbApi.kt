package com.yagosouza.yeahmovies.network

import com.yagosouza.yeahmovies.network.model.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/movie/day")
    fun getTrending(
        @Query("language") language: String,
        @Query("page") page: Integer
    ): Call<MovieResponseDTO>
}