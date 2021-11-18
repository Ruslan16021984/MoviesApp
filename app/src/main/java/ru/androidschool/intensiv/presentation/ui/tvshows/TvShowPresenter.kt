package ru.androidschool.intensiv.presentation.ui.tvshows

import android.annotation.SuppressLint
import android.util.Log
import ru.androidschool.intensiv.data.dto.tvshow.TvShow
import ru.androidschool.intensiv.domain.usecase.TvShowsUseCase
import ru.androidschool.intensiv.presentation.ui.base.BasePresenter

class TvShowPresenter(private val tvShowsUseCase: TvShowsUseCase) : BasePresenter<TvShowPresenter.TvShowView>() {

    @SuppressLint("CheckResult")
    fun getTvShows(){
        tvShowsUseCase.getTvShows().map { it.results }
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }.subscribe({
               view?.showTvShows(it)
            }, {
                Log.e("TAG", "onFailure: ${it.message}")
                view?.showError()
            })
    }
    interface TvShowView {
        fun showTvShows(listTvShow: List<TvShow>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError()
    }
}