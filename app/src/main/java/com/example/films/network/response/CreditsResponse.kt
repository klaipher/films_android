package com.example.films.network.response

import com.example.films.model.entities.Cast
import com.example.films.model.entities.Crew

class CreditsResponse(
    var id: Int,
    var cast: List<Cast>,
    var crew: List<Crew>
)