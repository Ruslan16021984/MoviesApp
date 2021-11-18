package ru.androidschool.intensiv.presentation.ui.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.movie.MovieData
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.PopularMovieUseCase
import ru.androidschool.intensiv.domain.usecase.TopRatedMoviesUseCase
import ru.androidschool.intensiv.presentation.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment), FeedPresenter.FeedView {
    private val presenter: FeedPresenter by lazy {
        FeedPresenter(TopRatedMoviesUseCase(MoviesRepositoryImpl()), PopularMovieUseCase(MoviesRepositoryImpl()))
    }
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
        presenter.attachView(this)
        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }
        presenter.getTopAndPopularMovies()
        movies_recycler_view.adapter = adapter

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

//    fun convertMovie(dto: MoviesDto): MovieDb {
//        return MovieDb(id = dto.id.toLong(),
//            title = dto.title, adult = dto.adult,
//        backdropPath = dto.backdrop_path,
//        genreIds = dto.genre_ids,
//        originalLanguage = dto.original_language,
//        originalTitle = dto.original_title,
//        overview = dto.overview,
//        popularity = dto.popularity,
//        posterPath = dto.poster_path,
//        releaseDate = dto.release_date,
//        video = dto.video,
//        voteAverage = dto.vote_average,
//        voteCount = dto.vote_count)
//    }

    override fun showMovies(movies: MovieData) {
        adapter.apply { addAll(movies.sourceTopRated) }
        adapter.apply { addAll(movies.sourceGetPopular) }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun showEmptyMovies() {
    }

    override fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt(KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    override fun showError() {

    }
}
