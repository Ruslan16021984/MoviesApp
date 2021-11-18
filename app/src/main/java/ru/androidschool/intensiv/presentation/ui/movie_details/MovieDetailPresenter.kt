package ru.androidschool.intensiv.presentation.ui.movie_details

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.data.dto.movieDetail.MovieCreditsDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieDetailDto
import ru.androidschool.intensiv.domain.usecase.CastAndCrewForMovieUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailUseCase
import ru.androidschool.intensiv.presentation.ui.base.BasePresenter
import timber.log.Timber

class MovieDetailPresenter(
    private val movieDetailUseCase: MovieDetailUseCase,
    private val castUseCase: CastAndCrewForMovieUseCase
) : BasePresenter<MovieDetailPresenter.DetailView>() {

    @SuppressLint("CheckResult")
    fun getMovieDetails(movieId: Int) {
        movieDetailUseCase.getMovieDetail(movieId)
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({view?.showDetailMovies(it)}, { t ->
                Timber.e(t, t.toString())
                view?.showEmptyMovies()})
    }

    @SuppressLint("CheckResult")
    fun getCastAndCrewForMovie(movieId: Int) {
        castUseCase.getCastAndCrewForMovie(movieId)
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({view?.showCastAndCrewMovies(it)}, { t ->
                Timber.e(t, t.toString())
                view?.showEmptyMovies()})
    }

    interface DetailView {
        fun showDetailMovies(movieDetailDto: MovieDetailDto)
        fun showCastAndCrewMovies(movieCreditsDto: MovieCreditsDto)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError()
    }
}