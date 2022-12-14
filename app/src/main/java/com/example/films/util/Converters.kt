package com.example.films.util

import androidx.room.TypeConverter
import com.example.films.model.entities.Collection
import com.example.films.model.entities.Genre
import com.example.films.model.entities.PersonalStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toPersonalStatus(status: Int): PersonalStatus {
        return when (status) {
            PersonalStatus.TO_SEE.status -> {
                PersonalStatus.TO_SEE
            }
            PersonalStatus.SEEN.status -> {
                PersonalStatus.SEEN
            }
            PersonalStatus.EMPTY.status -> {
                PersonalStatus.EMPTY
            }
            else -> throw IllegalArgumentException("Could not recognize status")
        }
    }


    @TypeConverter
    fun fromPersonalStatus(status: PersonalStatus?) = status?.status ?: 2

    @TypeConverter
    fun toSort(value: String) = enumValueOf<Sort>(value)

    @TypeConverter
    fun fromSort(value: Sort) = value.name

    @TypeConverter
    fun toGenreList(value: String?): List<Genre> {
        val listType = object : TypeToken<ArrayList<Genre?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromGenreList(list: List<Genre?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toCollection(value: String?): Collection? {
        val listType = object : TypeToken<Collection?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromCollection(list: Collection?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromListOfInt(list: List<Int>): String {
        return if(list.isNotEmpty())
            list.joinToString(separator = ",")
        else
            ""
    }

    @TypeConverter
    fun toListOfInt(value: String): List<Int> {
        return if (value.isNotEmpty())
            value.split(",").map(String::toInt)
        else
            listOf()
    }
}
