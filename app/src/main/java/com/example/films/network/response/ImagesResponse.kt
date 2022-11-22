package com.example.films.network.response

import com.example.films.model.entities.Image

class ImagesResponse(
    var id: Int,
    var backdrops: List<Image>,
    var posters: List<Image>
)