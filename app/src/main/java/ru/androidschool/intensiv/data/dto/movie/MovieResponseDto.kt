package ru.androidschool.intensiv.data.dto.movie

data class MovieResponseDto(
    val dates: DatesDto,
    val page: Int,
    val results: List<MoviesDto>,
    val total_pages: Int,
    val total_results: Int
)