package com.example.films.view.ui.filterable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.model.entities.Filter
import com.example.films.model.entities.Movie
import com.example.films.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


private const val TAG = "FilterableViewModel"


@HiltViewModel
class FilterableViewModel
@Inject
constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun setFilter(filter: Filter) = getFilteredMovies(filter)

    private fun getFilteredMovies(filter: Filter) {
        movieRepository.getDiscoverableMovies(filter)
            .mapLatest { movies ->
                _movies.postValue(movies)
            }
            .launchIn(viewModelScope)
    }

    fun getRandomMovie(): Movie? {
        return movies.value?.random()
    }


}