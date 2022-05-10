package com.yagosouza.yeahmovies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yagosouza.yeahmovies.AppConstants
import com.yagosouza.yeahmovies.network.ErrorResponse
import com.yagosouza.yeahmovies.network.NetworkResponse
import com.yagosouza.yeahmovies.network.model.dto.MovieDTO
import com.yagosouza.yeahmovies.repository.HomeDataSource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private var homeDataSourceMock: HomeDataSourceMock? = null

    private var moviesListMock: List<MovieDTO> = listOf(MovieDTO(
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
    ))
    private var listsOfMoviesMock: List<List<MovieDTO>> = listOf(moviesListMock, moviesListMock, moviesListMock)

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when LISTS OF MOVIES REQUEST returns SUCCESSFULLY expect livedata lists filled`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            homeDataSourceMock = HomeDataSourceMock(NetworkResponse.Success(listsOfMoviesMock))
            val viewModel = HomeViewModel(homeDataSourceMock!!, dispatcher)

            // Act (Onde a açao é executa)
            viewModel.getListsOfMovies()

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertEquals(listsOfMoviesMock, viewModel.listsOfMovies?.value)
            assertEquals(false, viewModel.isLoading?.value)
            assertEquals(false, viewModel.errorMessageVisibility?.value)
        }

    @Test
    fun `when LISTS OF MOVIES REQUEST returns API ERROR expect error livedata filled`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            homeDataSourceMock = HomeDataSourceMock(NetworkResponse.ApiError(ErrorResponse(), 0))
            val viewModel = HomeViewModel(homeDataSourceMock!!, dispatcher)

            // Act (Onde a açao é executa)
            viewModel.getListsOfMovies()

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertEquals(null, viewModel.listsOfMovies?.value)
            assertEquals(false, viewModel.isLoading?.value)
            assertEquals(true, viewModel.errorMessageVisibility?.value)
            assertEquals(AppConstants.API_ERROR_MESSAGE, viewModel.errorMessage?.value)
        }

    @Test
    fun `when LISTS OF MOVIES REQUEST returns NETWORK ERROR expect error livedata filled`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            homeDataSourceMock = HomeDataSourceMock(NetworkResponse.NetworkError(IOException()))
            val viewModel = HomeViewModel(homeDataSourceMock!!, dispatcher)

            // Act (Onde a açao é executa)
            viewModel.getListsOfMovies()

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertEquals(null, viewModel.listsOfMovies?.value)
            assertEquals(false, viewModel.isLoading?.value)
            assertEquals(true, viewModel.errorMessageVisibility?.value)
            assertEquals(AppConstants.NETWORK_ERROR_MESSAGE, viewModel.errorMessage?.value)
        }

    @Test
    fun `when LISTS OF MOVIES REQUEST returns UNKNOWN ERROR expect error livedata filled`() =
        dispatcher.runBlockingTest {
            // Arrange (arrumar as coisas para o teste)
            homeDataSourceMock = HomeDataSourceMock(NetworkResponse.UnknownError(Throwable()))
            val viewModel = HomeViewModel(homeDataSourceMock!!, dispatcher)

            // Act (Onde a açao é executa)
            viewModel.getListsOfMovies()

            // Assert (onde verifica se o resultado esperado foi obtido)
            assertEquals(null, viewModel.listsOfMovies?.value)
            assertEquals(false, viewModel.isLoading?.value)
            assertEquals(true, viewModel.errorMessageVisibility?.value)
            assertEquals(AppConstants.UNKNOWN_ERROR_MESSAGE, viewModel.errorMessage?.value)
        }
}

class HomeDataSourceMock(private val result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>): HomeDataSource {
    override suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit
    ) {
        homeResultCallback(result)
    }

}