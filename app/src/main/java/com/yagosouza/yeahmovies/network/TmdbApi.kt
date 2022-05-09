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
}