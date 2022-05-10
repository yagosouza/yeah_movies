package com.yagosouza.yeahmovies.repository

import com.yagosouza.yeahmovies.network.ErrorResponse
import com.yagosouza.yeahmovies.network.NetworkResponse
import com.yagosouza.yeahmovies.network.model.dto.MovieDTO
import kotlinx.coroutines.CoroutineDispatcher

interface HomeDataSource {

    suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit
    )
}