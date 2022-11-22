package com.example.films.util

enum class TimeWindow {
    WEEK, DAY;

    override fun toString(): String {
        return when(this){
            WEEK -> "week"
            DAY -> "day"
        }
    }
}