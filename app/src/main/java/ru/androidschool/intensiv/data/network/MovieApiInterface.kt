package ru.androidschool.intensiv.data.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.dto.movieDetail.MovieCreditsDto
import ru.androidschool.intensiv.data.dto.movieDetail.MovieDetailDto
import ru.androidschool.intensiv.data.dto.movie.MovieResponseDto
import ru.androidschool.intensiv.data.dto.tvshow.TvShowResponse

interface MovieApiInterface {
    @GET("movie/now_playing")
    fun getTopRatedMovies(@Query("language") language: String, @Query("page")page: Int):Call<MovieResponseDto>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("language") language: String, @Query("page")page: Int):Single<MovieResponseDto>

    @GET("movie/popular")
    fun getPopularMovie(@Query("language") language: String, @Query("page")page: Int):Single<MovieResponseDto>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id")movieId: Int, @Query("language") language: String):Single<MovieDetailDto>

    @GET("movie/{movie_id}/credits")
    fun getCastAndCrewForMovie(@Path("movie_id")movieId: Int, @Query("language") language: String): Single<MovieCreditsDto>

    @GET("tv/popular")
    fun getTvShow(@Query("language") language: String, @Query("page")page: Int):Single<TvShowResponse>

    @GET("search/movie")
    fun searchByQuery(@Query("language") language: String, @Query("query") query: String): Single<MovieResponseDto>
}