package com.example.films.model.local.dao

import androidx.room.*
import com.example.films.model.entities.Person
import kotlinx.coroutines.flow.Flow


@Dao
interface PersonDao : BaseDao<Person> {
    @Query("SELECT * FROM person")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT EXISTS(SELECT * FROM person WHERE id = :id)")
    fun isPersonFavorite(id: Int): Boolean

}