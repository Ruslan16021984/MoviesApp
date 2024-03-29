package ru.androidschool.intensiv.data.search

data class SearchResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)