package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.dto.tvshow.TvShowResponse
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class TvShowsUseCase(private val repository: MoviesRepository) {
    fun getTvShows():Single<TvShowResponse>{
       return repository.getTvShow().applySchedulers()
    }
}