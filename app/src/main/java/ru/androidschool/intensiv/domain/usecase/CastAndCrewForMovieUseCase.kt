package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.dto.movieDetail.MovieCreditsDto
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class CastAndCrewForMovieUseCase(private val repository: MoviesRepository) {

    fun getCastAndCrewForMovie(movieId: Int): Single<MovieCreditsDto> {
        return repository.getCastAndCrewForMovie(movieId)
    }
}