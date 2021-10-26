package ru.androidschool.intensiv.ui.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.movie.MovieData
import ru.androidschool.intensiv.data.movie.MovieResponse
import ru.androidschool.intensiv.data.movie.Movies
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FeedFragment : Fragment(R.layout.feed_fragment) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    @SuppressLint("CheckResult", "LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }
        val getTopRatedMovies = MovieApiClient.apiClient.getUpcomingMovies("ru", 1)
        val getPopularMovie = MovieApiClient.apiClient.getPopularMovie("ru", 1)
        Single.zip(
            getTopRatedMovies,
            getPopularMovie,
            BiFunction<MovieResponse, MovieResponse, MovieData> { source1, source2 ->
                val topRatedMoviesList = listOf(
                    source1.results.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
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
                    source2.results.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
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
            .doOnSubscribe { progress_bar.visibility = View.VISIBLE }
            .doFinally {progress_bar.visibility = View.INVISIBLE  }
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
            adapter.apply { addAll(it.sourceTopRated) }
            adapter.apply { addAll(it.sourceGetPopular) }
        }, {
            Log.e("TAG", "${it.message} " )
        })
        movies_recycler_view.adapter = adapter
    }

    private fun openMovieDetails(movie: Movies) {
        val bundle = Bundle()
        bundle.putInt(KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_ID = "id"
        const val KEY_SEARCH = "search"
    }
}
