package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.dto.movieDetail.MovieDetailDto
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class MovieDetailUseCase(private val repository: MoviesRepository) {
    fun getMovieDetail(movieId: Int): Single<MovieDetailDto> {
        return repository.getMovieDetail(movieId)
    }

}