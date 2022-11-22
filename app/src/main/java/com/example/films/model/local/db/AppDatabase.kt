package com.example.films.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.films.model.entities.Filter
import com.example.films.model.entities.Movie
import com.example.films.model.entities.Person
import com.example.films.model.local.dao.FilterDao
import com.example.films.model.local.dao.MovieDao
import com.example.films.model.local.dao.PersonDao
import com.example.films.util.Converters

@Database(
    entities = [Movie::class, Person::class, Filter::class],
    version = 13,
    exportSchema = false
)
@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun personDao(): PersonDao
    abstract fun filterDao(): FilterDao
}