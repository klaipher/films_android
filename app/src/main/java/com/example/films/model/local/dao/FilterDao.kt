package com.example.films.model.local.dao

import androidx.room.*
import com.example.films.model.entities.Filter
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDao : BaseDao<Filter> {

    @Query("SELECT * FROM filter")
    fun getAll(): Flow<List<Filter>>
}