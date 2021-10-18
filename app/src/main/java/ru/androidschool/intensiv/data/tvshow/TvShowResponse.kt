package ru.androidschool.intensiv.data.tvshow

data class TvShowResponse(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)