package com.example.films.util

enum class Sort(
    var order: Order = Order.DESC
) {
    POPULARITY(
        Order.DESC
    ),
    RELEASE_DATE(
        Order.DESC
    ),
    REVENUE(
        Order.DESC
    ),
    VOTE_AVERAGE(
        Order.DESC
    );

    override fun toString(): String {
        return when (this) {
            POPULARITY -> {
                "popularity.$order"
            }
            RELEASE_DATE -> {
                "release_date.$order"
            }
            VOTE_AVERAGE -> {
                "vote_average.$order"
            }
            REVENUE -> {
                "revenue.$order"
            }
        }
    }

    enum class Order {
        ASC, DESC;

        override fun toString(): String {
            return when (this) {
                ASC -> "asc"
                DESC -> "desc"
            }
        }


    }
}