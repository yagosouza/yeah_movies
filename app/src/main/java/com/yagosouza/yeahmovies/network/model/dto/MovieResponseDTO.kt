package com.yagosouza.yeahmovies.network.model.dto

data class MovieResponseDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)