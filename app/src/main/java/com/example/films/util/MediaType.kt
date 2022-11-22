package com.example.films.util

enum class MediaType(
    var value: String
) {
    ALL("all"), MOVIE("movie"), TV("tv"), PERSON("person");

    override fun toString(): String {
        return value
    }
}