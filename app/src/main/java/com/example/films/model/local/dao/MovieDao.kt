package com.example.films.model.local.dao

import androidx.room.*
import com.example.films.model.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getById(id: Int): Movie?

    @Query("SELECT * FROM movie WHERE personalStatus=1")
    fun getToSeeMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE personalStatus=0")
    fun getSeenMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE favorite=1")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :id AND favorite = 1)")
    fun isFavorite(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :id AND personalStatus = 1 )")
    fun isToSee(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :id AND personalStatus = 0)")
    fun isSeen(id: Int): Boolean


}