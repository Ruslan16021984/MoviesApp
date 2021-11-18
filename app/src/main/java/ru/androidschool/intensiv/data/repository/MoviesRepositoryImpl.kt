package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.dto.movie.MovieResponseDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieCreditsDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieDetailDto
import ru.androidschool.intensiv.data.dto.tvshow.TvShowResponse
import ru.androidschool.intensiv.data.mapper.MovieMapper
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository
import ru.androidschool.intensiv.data.network.MovieApiClient

class MoviesRepositoryImpl:
    MoviesRepository {
    override fun getMovies(): Single<List<Movie>> {
        return MovieApiClient.apiClient.getUpcomingMovies("ru", 1)
            .map { MovieMapper.toValueObject(it) }

    }

    override fun getPopularMovie(): Single<List<Movie>> {
        return MovieApiClient.apiClient.getPopularMovie("ru", 1)
            .map { MovieMapper.toValueObject(it) }
    }

    override fun getMovieDetail(movieId: Int): Single<MovieDetailDto> {
        return MovieApiClient.apiClient.getMovieDetail(movieId, "ru")
    }

    override fun getCastAndCrewForMovie(movieId: Int): Single<MovieCreditsDto> {
        return MovieApiClient.apiClient.getCastAndCrewForMovie(movieId, "ru")
    }

    override fun getTvShow(): Single<TvShowResponse> {
        return MovieApiClient.apiClient.getTvShow("ru", 1)
    }

    override fun searchByQuery(query: String): Single<MovieResponseDto> {
        return MovieApiClient.apiClient.searchByQuery("ru", query)
    }
}