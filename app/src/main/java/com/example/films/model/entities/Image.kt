package com.example.films.model.entities


class Image(
    var file_path: String,
    var height: Int,
    var width: Int
) {
    val imageType: Int
        get() = if (height > width) POSTER else BACKDROP

    companion object {
        const val BACKDROP = 0
        const val POSTER = 1
    }
}