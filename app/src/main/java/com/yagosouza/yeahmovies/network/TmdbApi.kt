package com.yagosouza.yeahmovies.network

import com.yagosouza.yeahmovies.network.model.dto.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/movie/day")
    suspend fun getTrending(
        @Query("language") language: String,
        @Query("page") page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String,
        @Query("page") page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") language: String,
        @Query("page") page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/top-rated")
    suspend fun getTopRated(
        @Query("language") language: String,
        @Query("page") page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>



}