package com.example.films.view.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.model.entities.Movie
import com.example.films.model.entities.Person
import com.example.films.model.repository.ActorRepository
import com.example.films.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "SearchViewModel"


@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val movieRepository: MovieRepository,
    private val actorRepository: ActorRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    private val _queriedMovies = MutableLiveData<List<Movie>>()
    val queriedMovies: LiveData<List<Movie>> get() = _queriedMovies

    private val _queriedActors = MutableLiveData<List<Person>>()
    val queriedActors: LiveData<List<Person>> get() = _queriedActors

    init {
        clearQuery()
    }

    fun setQuery(query: String) {
        _query.value = query
        searchMovies(query)
        searchActors(query)
    }

    private fun searchActors(query: String) = viewModelScope.launch {
        try {
            movieRepository.getMoviesBySearch(query).collectLatest { movies ->

                _queriedMovies.value = movies

            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

    }

    private fun searchMovies(query: String) = viewModelScope.launch {
        try {
            actorRepository.getPeoplesBySearch(query).collectLatest { response ->

                _queriedActors.value = response

            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

    }

    private fun clearQuery() {
        _query.value = ""
    }

}