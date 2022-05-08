package com.yagosouza.yeahmovies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yagosouza.yeahmovies.network.TmdbApi
import com.yagosouza.yeahmovies.network.model.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(val tmdbApi: TmdbApi) : ViewModel() {

    fun getTrending() {
        tmdbApi.getTrending("pt-BR", 1).enqueue(object : Callback<MovieResponseDTO> {
            override fun onResponse(
                call: Call<MovieResponseDTO>,
                response: Response<MovieResponseDTO>
            ) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
                Log.d("FALHA NA REQUISIÇÃO", "onFailure")
            }

        })
    }
}