package ru.androidschool.intensiv.presentation.ui.tvshows

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.tvshow.TvShow
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.domain.usecase.TvShowsUseCase


class TvShowsFragment : Fragment(R.layout.tv_shows_fragment), TvShowPresenter.TvShowView {
    private val presenter: TvShowPresenter by lazy {
        TvShowPresenter(
            TvShowsUseCase(MoviesRepositoryImpl())
        )
    }
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.getTvShows()
    }

    override fun showTvShows(listTvShow: List<TvShow>) {
        val showList = listTvShow.map { TvShowItem(it) }.toList()
        adapter.apply { showList.let { addAll(it) } }
        tv_shows_recycler_view.adapter = adapter
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showEmptyMovies() {
    }

    override fun showError() {
    }


}
