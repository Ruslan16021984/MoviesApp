package ru.androidschool.intensiv.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.movidetail.MovieCredits
import ru.androidschool.intensiv.data.movidetail.MovieDetail
import ru.androidschool.intensiv.data.movie.MovieResponse
import ru.androidschool.intensiv.data.tvshow.TvShowResponse

interface MovieApiInterface {
    @GET("movie/now_playing")
    fun getTopRatedMovies(@Query("language") language: String, @Query("page")page: Int):Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("language") language: String, @Query("page")page: Int):Single<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovie(@Query("language") language: String, @Query("page")page: Int):Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id")movieId: Int, @Query("language") language: String):Call<MovieDetail>

    @GET("movie/{movie_id}/credits")
    fun getCastAndCrewForMovie(@Path("movie_id")movieId: Int, @Query("language") language: String): Call<MovieCredits>

    @GET("tv/popular")
    fun getTvShow(@Query("language") language: String, @Query("page")page: Int):Single<TvShowResponse>

    @GET("search/movie")
    fun searchByQuery(@Query("language") language: String, @Query("query") query: String): Single<MovieResponse>
}