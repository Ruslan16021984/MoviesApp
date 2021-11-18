package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class PopularMovieUseCase(private val repository: MoviesRepository) {
    fun getMovies(): Single<List<Movie>> {
        return repository.getPopularMovie().applySchedulers()
    }
}