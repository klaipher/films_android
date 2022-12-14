package com.example.films.model.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.films.util.Converters
import java.io.Serializable
import java.text.NumberFormat
import java.util.*

@Entity
class Movie(
    @TypeConverters(Converters::class)
    val belongs_to_collection: Collection?,
    @TypeConverters(Converters::class)
    val genres: List<Genre>?,
    @PrimaryKey
    val id: Int,
    val poster_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val title: String,
    val vote_average: Double,
    var personalStatus: PersonalStatus = PersonalStatus.EMPTY,
    var favorite: Boolean = false,
    val original_title: String,
    @Ignore
    val original_language: String,
    @Ignore
    val homepage: String?,
    @Ignore
    val imdb_id: String?,
    @Ignore
    val backdrop_path: String?,
    @Ignore
    val overview: String?,
    @Ignore
    val budget: Int,
    @Ignore
    val popularity: Double,
    @Ignore
    val adult: Boolean,
    @Ignore
    val revenue: Int,
    @Ignore
    val status: String?,
    @Ignore
    val tagline: String?,
    @Ignore
    val video: Boolean,
    @Ignore
    val vote_count: Int
) : Serializable {

    val formattedRuntime: String
        get():String {
            return if (runtime != null) {
                val hours = (runtime / 60) //since both are ints, you get an int
                val minutes = (runtime % 60)
                String.format("%d h %02d min", hours, minutes)
            } else ""
        }

    val formattedBudget: String
        get() {
            val current = Locale.getDefault()
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency =
                Currency.getInstance(Currency.getInstance(current).currencyCode)
            return format.format(budget)
        }

    val formattedRevenue: String
        get() {
            val current = Locale.getDefault()
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency =
                Currency.getInstance(Currency.getInstance(current).currencyCode)
            return format.format(revenue)
        }

    constructor(
        genres: List<Genre>?,
        belongs_to_collection: Collection?,
        id: Int,
        original_title: String,
        poster_path: String?,
        release_date: String?,
        runtime: Int?,
        title: String,
        vote_average: Double,
        personalStatus: PersonalStatus = PersonalStatus.EMPTY,
        favorite: Boolean
    ) : this(
        belongs_to_collection, genres, id, poster_path, release_date, runtime, title, vote_average,
        personalStatus, favorite, original_title, "", "", "", "",
        "", 0, 0.0, false, 0, "", "", false, 0
    )

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false

        return true
    }


}


@TypeConverters(Converters::class)
enum class PersonalStatus(
    val status: Int
) {
    TO_SEE(1), SEEN(0), EMPTY(2);
}