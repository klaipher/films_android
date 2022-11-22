package com.example.films.network.service


import com.example.films.model.entities.Collection
import com.example.films.network.response.CreditsResponse
import com.example.films.network.response.GenericResponse
import com.example.films.model.entities.Genre
import com.example.films.network.response.ImagesResponse
import com.example.films.model.entities.Movie
import com.example.films.model.entities.Video
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopular(@QueryMap queries: HashMap<String, String>): GenericResponse<Movie>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@QueryMap queries: HashMap<String, String>): GenericResponse<Movie>

    @GET("movie/top_rated")
    suspend fun getTopRated(@QueryMap queries: HashMap<String, String>): GenericResponse<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @QueryMap queries: HashMap<String, String>
    ): Movie

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Int,
        @QueryMap queries: HashMap<String, String>
    ): CreditsResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movie_id: Int,
        @QueryMap queries: HashMap<String, String>
    ): GenericResponse<Movie>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movie_id: Int,
        @QueryMap queries: HashMap<String, String>
    ): GenericResponse<Video>


    @GET("movie/{movie_id}/images")
    suspend fun getImages(
        @Path("movie_id") movie_id: Int,
        @QueryMap queries: HashMap<String, String>
    ): ImagesResponse

    @GET("collection/{collection_id}")
    suspend fun getCollection(
        @Path("collection_id") id: Int,
        @QueryMap queries: HashMap<String, String>
    ): Collection

    @GET("search/movie")
    suspend fun getMoviesBySearch(@QueryMap queries: HashMap<String, String>): GenericResponse<Movie>

    @GET("discover/movie")
    suspend fun getMoviesByDiscover(@QueryMap queries: HashMap<String, String>): GenericResponse<Movie>

    @GET("genre/movie/list")
    suspend fun getGenreList(@QueryMap queries: HashMap<String, String>): GenericResponse<Genre>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") media_type: String,
        @Path("time_window") time_window: String,
        @QueryMap queries: HashMap<String, String>
    ): GenericResponse<Movie>

}