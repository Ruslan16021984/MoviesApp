package ru.androidschool.intensiv.data.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.data.movidetail.MovieDetail

@Dao
interface TheMovieDbDao {
    @Delete
    fun deleteMovie(movieDb: MovieDb):Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(moviesListL: List<MovieDb>):Completable

    @Query("SELECT * FROM MovieDb")
    fun getMoviesList(): Observable<List<MovieDb>>

}