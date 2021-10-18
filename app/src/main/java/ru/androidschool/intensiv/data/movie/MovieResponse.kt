package ru.androidschool.intensiv.data.movie

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movies>,
    val total_pages: Int,
    val total_results: Int
)