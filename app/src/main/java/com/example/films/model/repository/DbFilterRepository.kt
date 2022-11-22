package com.example.films.model.repository

import com.example.films.model.entities.Filter
import com.example.films.model.local.dao.FilterDao
import com.example.films.model.local.db.AppDatabase
import javax.inject.Inject

class DbFilterRepository
@Inject
constructor(appDatabase: AppDatabase) {
    companion object {
        private lateinit var filterDao: FilterDao
    }

    init {
        filterDao = appDatabase.filterDao()
    }

    fun getFilters() = filterDao.getAll()
    fun insertFilter(filter: Filter) = filterDao.insert(filter)
    fun deleteFilter(filter: Filter) = filterDao.delete(filter)
}