package ru.androidschool.intensiv.presentation.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.movie.MovieResponseDto
import ru.androidschool.intensiv.data.dto.movie.MoviesDto
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.presentation.ui.feed.FeedFragment
import ru.androidschool.intensiv.presentation.ui.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.presentation.ui.feed.MovieSearchItem
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_toolbar.onTextChangedObservable.map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.length > 2 }
            .observeOn(Schedulers.io())
            .flatMapSingle { MovieApiClient.apiClient.searchByQuery("ru", it) }
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                setMovies(it)
            },
                {
                    Log.e("TAG", "Error: ${it.message}")
                }
            )
        val getArgs = arguments?.getString(KEY_SEARCH, "")
        search_toolbar.search_edit_text.setText(getArgs)
    }
    private fun setMovies(movieResponse: MovieResponseDto) {
        val movieList = movieResponse.results.map {
            MovieSearchItem(it) { movie ->
                openMovieDetails(
                    movie
                )
            }
        }
        movies_recycler_view.adapter = adapter.apply { addAll(movieList) }
    }
    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    private fun openMovieDetails(movie: MoviesDto) {
        val bundle = Bundle()
        bundle.putInt(FeedFragment.KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
