package com.yagosouza.yeahmovies.network.model.dto

data class MovieResponseDTO(
    val page: Int,
    val moviesDTO: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)