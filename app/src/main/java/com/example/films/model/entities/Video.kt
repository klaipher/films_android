package com.example.films.model.entities

class Video(
    var id: String,
    var key: String,
    var type: String,
    var name: String,
    var site: String,
    var published_at: String,
    var isOfficial: Boolean
) {

    override fun toString(): String {
        return "Video{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", published_at='" + published_at + '\'' +
                ", official=" + isOfficial +
                '}'
    }
}