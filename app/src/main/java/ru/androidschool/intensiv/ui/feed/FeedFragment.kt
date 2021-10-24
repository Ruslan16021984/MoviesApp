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
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.movie.MovieResponse
import ru.androidschool.intensiv.data.movie.Movies
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import ru.androidschool.intensiv.ui.tvshows.TvShowItem
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

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Observable.create(ObservableOnSubscribe<String> {
            search_toolbar.search_edit_text.afterTextChanged { text ->
                it.onNext(text.toString())
            }
        }).map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.length > 2 }
            .observeOn(Schedulers.io())
            .flatMapSingle { MovieApiClient.apiClient.searchByQuery("ru", it) }
            .observeOn(AndroidSchedulers.mainThread()).subscribe (
                {setMovies(it)},
                {
                    Log.e("TAG", "Error: ${it.message}" )
                }
            )
//        search_toolbar.search_edit_text.afterTextChanged {
//            Timber.d(it.toString())
//            if (it.toString().length > MIN_LENGTH) {
//                openSearch(it.toString())
//            }
//        }
        val getTopRatedMovies = MovieApiClient.apiClient.getUpcomingMovies("ru", 1)
        val getPopularMovie = MovieApiClient.apiClient.getPopularMovie("ru", 1)
        getTopRatedMovies.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                // Передаем результат в adapter и отображаем элементы
                val moviesList = listOf(
                    response.body()?.results?.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
                                movie
                            )
                        }
                    }?.let {
                        MainCardContainer(
                            R.string.recommended,
                            it.toList()
                        )
                    }
                )
                adapter.apply { addAll(moviesList) }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
        getPopularMovie.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val moviesList = listOf(
                    response.body()?.results?.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
                                movie
                            )
                        }
                    }?.let {
                        MainCardContainer(
                            R.string.upcoming,
                            it.toList()
                        )
                    }
                )
                adapter.apply { addAll(moviesList) }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }


        })

        movies_recycler_view.adapter = adapter
    }
    private fun setMovies(movieResponse: MovieResponse){
        val movieList = movieResponse.results.map { MovieSearchItem(it){ movie ->
            openMovieDetails(
                movie
            )
        } }
        movies_recycler_view.adapter = adapter.apply { addAll(movieList) }
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
