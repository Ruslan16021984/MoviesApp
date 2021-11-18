package ru.androidschool.intensiv.data.dto.movie

import ru.androidschool.intensiv.presentation.ui.feed.MainCardContainer

data class MovieData(
    val sourceTopRated: List<MainCardContainer>,
    val sourceGetPopular: List<MainCardContainer>
)