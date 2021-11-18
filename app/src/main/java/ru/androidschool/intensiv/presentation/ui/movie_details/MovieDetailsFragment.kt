package ru.androidschool.intensiv.presentation.ui.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.db.TheMovieDatabase
import ru.androidschool.intensiv.data.dto.movieDetail.MovieCreditsDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieDetailDto
import ru.androidschool.intensiv.data.dto.movieDetail.convertFromGenresTo
import ru.androidschool.intensiv.data.dto.movieDetail.convertFromMovieToDetail
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.domain.usecase.CastAndCrewForMovieUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailUseCase
import ru.androidschool.intensiv.presentation.ui.feed.FeedFragment.Companion.KEY_ID

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment), MovieDetailPresenter.DetailView {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private val presenter: MovieDetailPresenter by lazy {
        MovieDetailPresenter(
            MovieDetailUseCase(MoviesRepositoryImpl()), CastAndCrewForMovieUseCase(
                MoviesRepositoryImpl()
            )
        )
    }
    private var movieDetail: MovieDetailDto? = null

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)
        val getArgs = arguments?.getInt(KEY_ID)
        val db = TheMovieDatabase.getInstance(requireContext()).movieDetailDao()
        checkbox_like.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                movieDetail?.let {
                    db.saveMovieDetail(convertFromMovieToDetail(it))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                            Log.e("TAG", "onViewCreated: ${it.id}")
                        }, {
                            Log.e("TAG", "onViewCreated: ${it.message}")
                        })
                    db.saveGenres(convertFromGenresTo(it.id, it.genres)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                            Log.e("TAG", "onViewCreated: ${it.id}")
                        }, {
                            Log.e("TAG", "onViewCreated: ${it.message}")
                        })
                }
            }
        }
        getArgs?.let { presenter.getCastAndCrewForMovie(it) }
        getArgs?.let { presenter.getMovieDetails(it) }
        items_container.adapter = adapter

    }

    override fun showDetailMovies(movies: MovieDetailDto) {
        tv_title.text = movies.title ?: ""
        val genres = movies.genres.map { it.name }.toString()
        tv_genre.text = genres.substring(1, genres.length - 1)
        description.text = movies.overview ?: ""
        movie_rating.rating = movies.rating ?: 0f
        tv_year.text = movies.release_date
        val companies = movies.production_companies.map { it.name }.toString()
        tv_studio.text = companies.substring(1, companies.length - 1)
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${movies.poster_path}")
            .into(img_detail)
        movieDetail = movies
    }

    override fun showCastAndCrewMovies(movieCredits: MovieCreditsDto) {
       val castList= movieCredits.cast.map { CastItem(it) }.toList()
        adapter.apply { addAll(castList) }
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

