package com.example.randommovie.data.dto

class MovieDto (
    val title: String,
    val director: String,
    val year: Int,
    val genre: List<String>,
    val actors: List<String>,
    val rating: Double
)