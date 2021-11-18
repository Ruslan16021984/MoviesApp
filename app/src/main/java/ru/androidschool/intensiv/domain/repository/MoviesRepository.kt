package ru.androidschool.intensiv.domain.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.dto.movie.MovieResponseDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieCreditsDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieDetailDto
import ru.androidschool.intensiv.data.dto.tvshow.TvShowResponse
import ru.androidschool.intensiv.data.vo.Movie

interface MoviesRepository {
    fun getMovies(): Single<List<Movie>>
    fun getPopularMovie(): Single<List<Movie>>
    fun getMovieDetail(movieId: Int): Single<MovieDetailDto>
    fun getCastAndCrewForMovie(movieId: Int): Single<MovieCreditsDto>
    fun getTvShow(): Single<TvShowResponse>
    fun searchByQuery(query: String): Single<MovieResponseDto>
}