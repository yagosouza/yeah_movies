package com.yagosouza.yeahmovies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yagosouza.yeahmovies.AppConstants
import com.yagosouza.yeahmovies.network.ErrorResponse
import com.yagosouza.yeahmovies.network.NetworkResponse
import com.yagosouza.yeahmovies.network.TmdbApi
import com.yagosouza.yeahmovies.network.model.dto.MovieDTO
import com.yagosouza.yeahmovies.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class HomeDataSourceImplTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var homeDataSourceImpl: HomeDataSourceImpl

    @Mock
    private lateinit var tmdbApi: TmdbApi

    private val movieResponseDTO =
        MovieResponseDTO(
            0, listOf(
                MovieDTO(
                    adult = false,
                    backdrop_path = "",
                    genre_ids = listOf(0, 0),
                    id = 0,
                    media_type = "",
                    original_language = "",
                    original_title = "",
                    overview = "",
                    popularity = 0.0,
                    poster_path = "",
                    release_date = "",
                    title = "",
                    video = false,
                    vote_average = 0.0,
                    vote_count = 0
                )
            ), 0, 0
        )

    @Before
    fun init() {
        homeDataSourceImpl = HomeDataSourceImpl(tmdbApi)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when ALL 4 REQUEST returns SUCCESSFULLY expect success network response`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getUpcoming(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getPopular(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getTopRated(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )

            var response: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>? = null

            // Act (Onde a açao é executa)
            homeDataSourceImpl.getListOfMovies(dispatcher) {
                response = it
            }

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertTrue(response is NetworkResponse.Success)
            assertEquals(movieResponseDTO.results, (response as NetworkResponse.Success).body[0])
        }

    @Test
    fun `when 1 OF THE REQUEST returns API ERROR expect api error response`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.ApiError(ErrorResponse(), 400)
            )
            `when`(tmdbApi.getUpcoming(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getPopular(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getTopRated(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )

            var response: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>? = null

            // Act (Onde a açao é executa)
            homeDataSourceImpl.getListOfMovies(dispatcher) {
                response = it
            }

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertTrue(response is NetworkResponse.ApiError)
            assertEquals(NetworkResponse.ApiError(ErrorResponse(), 400).body, (response as NetworkResponse.ApiError).body)
        }

    @Test
    fun `when 1 OF THE REQUEST returns NETWORK ERROR expect network error response`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.NetworkError(IOException())
            )
            `when`(tmdbApi.getUpcoming(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getPopular(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getTopRated(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )

            var response: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>? = null

            // Act (Onde a açao é executa)
            homeDataSourceImpl.getListOfMovies(dispatcher) {
                response = it
            }

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertTrue(response is NetworkResponse.NetworkError)
        }

    @Test
    fun `when 1 OF THE REQUEST returns UNKNOWN ERROR expect unknown error response`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.UnknownError(Throwable())
            )
            `when`(tmdbApi.getUpcoming(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getPopular(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )
            `when`(tmdbApi.getTopRated(AppConstants.LANGUAGE, 1)).thenReturn(
                NetworkResponse.Success(movieResponseDTO)
            )

            var response: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>? = null

            // Act (Onde a açao é executa)
            homeDataSourceImpl.getListOfMovies(dispatcher) {
                response = it
            }

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertTrue(response is NetworkResponse.UnknownError)
        }
}