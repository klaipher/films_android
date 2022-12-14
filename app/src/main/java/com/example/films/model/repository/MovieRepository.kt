package com.example.films.model.repository

import com.example.films.network.service.MovieService
import com.example.films.model.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import com.example.films.model.entities.Collection

class MovieRepository
@Inject
constructor(
    private val movieService: MovieService
) {
    private val defaultSystemLanguage: String = Locale.getDefault().language

    companion object {
        private lateinit var sMap: HashMap<String, String>
    }

    init {
        sMap = HashMap()
        sMap["language"] =
            defaultSystemLanguage
        sMap["append_to_response"] = "images"
        sMap["include_image_language"] =
            defaultSystemLanguage
        sMap["page"] = "1"
    }

    suspend fun getPopularMovies(): Flow<List<Movie>> = flow {
        val popular = movieService.getPopular(sMap).results
        emit(popular)
    }

    suspend fun getTopRatedMovies(): Flow<List<Movie>> = flow {
        val topRated = movieService.getTopRated(sMap).results
        emit(topRated)
    }

    suspend fun getUpcomingMovies(): Flow<List<Movie>> = flow {
        val upcoming = movieService.getUpcoming(sMap).results
        emit(upcoming)
    }

    suspend fun getGenreList(): Flow<List<Genre>> = flow {
        val genres = movieService.getGenreList(sMap).results
        emit(genres)
    }

    suspend fun getTrendingMovies(mediaType: String, timeWindow: String): Flow<List<Movie>> = flow {
        val trending = movieService.getTrendingMovies(mediaType, timeWindow, sMap).results
        emit(trending)
    }


    fun getVideos(movieId: Int): Flow<List<Video>> = flow {
        val videos = movieService.getVideos(movieId, sMap).results
        emit(videos)
    }

    fun getMovieDetails(movieId: Int): Flow<Movie> = flow {
        emit(movieService.getMovieDetails(movieId, sMap))
    }


    fun getSimilarMovies(movieId: Int): Flow<List<Movie>> = flow {
        val similarMovies = movieService.getSimilar(movieId, sMap).results
        emit(similarMovies)
    }

    fun getDiscoverableMovies(filter: Filter): Flow<List<Movie>> = flow {
        sMap["sort_by"] =
            filter.sortBy.toString()
        sMap["with_genres"] =
            filter.withGenres
                .toString()
                .replace("[", "")
                .replace("]", "")
        val movies = movieService.getMoviesByDiscover(sMap).results
        emit(movies)
    }

    fun getPosters(movieId: Int): Flow<List<Image>> = flow {
        val posters = movieService.getImages(movieId, sMap).posters
        emit(posters)
    }

    fun getBackdrops(movieId: Int): Flow<List<Image>> = flow {
        val backdrops = movieService.getImages(movieId, sMap).backdrops
        emit(backdrops)
    }

    fun getMovieCast(movieId: Int): Flow<List<Cast>> = flow {
        val cast = movieService.getMovieCredits(movieId, sMap).cast
        emit(cast)
    }

    fun getMovieCrew(movieId: Int): Flow<List<Crew>> = flow {
        val cast = movieService.getMovieCredits(movieId, sMap).crew
        emit(cast)
    }


    fun getCollection(collectionId: Int): Flow<Collection> = flow {
        val collection = movieService.getCollection(collectionId, sMap)
        emit(collection)
    }


    suspend fun getMoviesBySearch(query: String): Flow<List<Movie>> = flow {
        sMap["query"] = query
        emit(movieService.getMoviesBySearch(sMap).results)
    }

    suspend fun getMoviesByActor(with_cast: String): Flow<List<Movie>> = flow {
        sMap["with_cast"] = with_cast
        emit(movieService.getMoviesByDiscover(sMap).results)
    }


}

