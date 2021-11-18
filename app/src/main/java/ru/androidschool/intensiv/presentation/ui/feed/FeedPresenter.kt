package ru.androidschool.intensiv.presentation.ui.feed

import android.annotation.SuppressLint
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.movie.MovieData
import ru.androidschool.intensiv.data.dto.movie.MovieResponseDto
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.PopularMovieUseCase
import ru.androidschool.intensiv.domain.usecase.TopRatedMoviesUseCase
import ru.androidschool.intensiv.presentation.ui.base.BasePresenter
import timber.log.Timber

class FeedPresenter(
    private val topRatedUseCase: TopRatedMoviesUseCase,
    private val popularMovieUseCase: PopularMovieUseCase
) : BasePresenter<FeedPresenter.FeedView>() {

    @SuppressLint("CheckResult")
    fun getTopAndPopularMovies() {
        Single.zip(
            topRatedUseCase.getMovies(),
            popularMovieUseCase.getMovies(),
            BiFunction<List<Movie>, List<Movie>, MovieData> { source1, source2 ->
                val topRatedMoviesList = listOf(
                    source1.map {
                        MovieItem(it) { movie ->
                            view?.openMovieDetails(
                                movie
                            )
                        }
                    }.let {
                        MainCardContainer(
                            R.string.recommended,
                            it.toList()
                        )
                    }
                )
                val getPopularList = listOf(
                    source2.map {
                        MovieItem(it) { movie ->
                            view?.openMovieDetails(
                                movie
                            )
                        }
                    }.let {
                        MainCardContainer(
                            R.string.upcoming,
                            it.toList()
                        )
                    }
                )
                MovieData(sourceTopRated = topRatedMoviesList, sourceGetPopular = getPopularList)
            }).subscribeOn(Schedulers.io())
            .doOnSubscribe { view?.showLoading()}
            .doFinally { view?.hideLoading() }
            .observeOn(AndroidSchedulers.mainThread()).subscribe({view?.showMovies(it)}, { t ->
                Timber.e(t, t.toString())
                view?.showEmptyMovies()})
    }

    interface FeedView {
        fun showMovies(movies: MovieData)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun openMovieDetails(movie: Movie)
        fun showError()
    }
}