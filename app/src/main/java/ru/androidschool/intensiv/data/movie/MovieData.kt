package ru.androidschool.intensiv.data.movie

import ru.androidschool.intensiv.ui.feed.MainCardContainer

data class MovieData(
    val sourceTopRated: List<MainCardContainer>,
    val sourceGetPopular: List<MainCardContainer>
)