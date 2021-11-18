package ru.androidschool.intensiv.presentation.ui.watchlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_watchlist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.db.TheMovieDatabase
import ru.androidschool.intensiv.data.dto.movieDetail.convertFromDetailToMovie
import ru.androidschool.intensiv.domain.sealds.MovieStates
import timber.log.Timber

class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    lateinit var watchViewModel: WatchViewModel
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchViewModel = ViewModelProvider(requireActivity()).get(WatchViewModel::class.java)

        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        val db = TheMovieDatabase.getInstance(requireContext()).movieDetailDao()
        watchViewModel.getMyFavoriteMovies(db)

        watchViewModel.movieDetailGenres.observe(viewLifecycleOwner,
            Observer {
                event(it)
            })
        movies_recycler_view.adapter = adapter
    }

    private fun event(states: MovieStates?) {
        when (states) {
            MovieStates.Loading -> {
                showLoading()
            }
            is MovieStates.Loaded -> {
                hideLoading()
                val moviesList = states.data.map {
                    MoviePreviewItem(
                        convertFromDetailToMovie(it)
                    ) { movie -> }
                }.toList()
                adapter.apply { addAll(moviesList) }
            }
            MovieStates.CancelLoading -> {
                hideLoading()
            }
            is MovieStates.Error -> {
                showEmptyMovies()
                Timber.e(states.message)
            }
            null -> {
                showEmptyMovies()
            }
        }
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.INVISIBLE
    }

    private fun showEmptyMovies() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
