package com.yagosouza.yeahmovies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yagosouza.yeahmovies.AppConstants
import com.yagosouza.yeahmovies.di.IODispatcher
import com.yagosouza.yeahmovies.network.NetworkResponse
import com.yagosouza.yeahmovies.network.TmdbApi
import com.yagosouza.yeahmovies.network.model.dto.MovieDTO
import com.yagosouza.yeahmovies.network.model.dto.MovieResponseDTO
import com.yagosouza.yeahmovies.repository.HomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

val LOGTAG = "logrequest"

class HomeViewModel @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val homeDataSource: HomeDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _listsOfMovies: MutableLiveData<List<List<MovieDTO>>>? = MutableLiveData()
    val listsOfMovies: LiveData<List<List<MovieDTO>>>? = _listsOfMovies

    private val _errorMessage: MutableLiveData<String>? = MutableLiveData()
    val errorMessage: LiveData<String>? = _errorMessage

    private val _errorMessageVisibility: MutableLiveData<Boolean>? = MutableLiveData()
    val errorMessageVisibility: LiveData<Boolean>? = _errorMessageVisibility

    private val _isLoading: MutableLiveData<Boolean>? = MutableLiveData()
    val isLoading: LiveData<Boolean>? = _isLoading

    fun getListsOfMovies() {
        showErrorMessage(false)
        try {
            viewModelScope.launch(dispatcher) {
                homeDataSource.getListOfMovies(dispatcher) { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            _listsOfMovies?.value = result.body
                            _isLoading?.value = false
                            _errorMessageVisibility?.value = false
                        }
                        is NetworkResponse.NetworkError -> {
                            showErrorMessage(true, AppConstants.NETWORK_ERROR_MESSAGE)
                        }
                        is NetworkResponse.ApiError -> {
                            showErrorMessage(true, AppConstants.API_ERROR_MESSAGE)
                        }
                        is NetworkResponse.UnknownError -> {
                            showErrorMessage(true, AppConstants.UNKNOWN_ERROR_MESSAGE)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun showErrorMessage(show: Boolean, message: String? = null) {
        _isLoading?.value = !show
        _errorMessageVisibility?.value = show
        _errorMessage?.value = message
    }
}