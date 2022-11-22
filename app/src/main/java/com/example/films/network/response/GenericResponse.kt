package com.example.films.network.response

import kotlinx.serialization.SerialName

class GenericResponse<T>(
    val page: Int?,
    @SerialName(value = "total_pages")
    val totalPages: Int?,
    @SerialName(value = "total_results")
    val totalResults: Int?,
    val results: List<T> = listOf()
)