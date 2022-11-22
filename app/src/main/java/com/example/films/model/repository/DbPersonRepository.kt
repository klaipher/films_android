package com.example.films.model.repository

import com.example.films.model.entities.Person
import com.example.films.model.local.dao.PersonDao
import com.example.films.model.local.db.AppDatabase
import javax.inject.Inject

class DbPersonRepository
@Inject
constructor(appDatabase: AppDatabase) {
    companion object {
        private lateinit var personDao: PersonDao
    }

    init {
        personDao = appDatabase.personDao()
    }

    fun getPersons() = personDao.getAll()
    fun isPersonFavorite(id: Int) = personDao.isPersonFavorite(id)
    fun insertPerson(person: Person) = personDao.insert(person)
    fun updatePerson(person: Person) = personDao.update(person)
    fun deletePerson(person: Person) = personDao.delete(person)


}