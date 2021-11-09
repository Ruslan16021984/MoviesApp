package ru.androidschool.intensiv.data.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun saveMovieDetail(movieDetailDb: MovieDetailDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveGenres(genreList: List<GenreDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveGenre(genreDb: GenreDb): Completable

    @Transaction
    @Query("SELECT * FROM MovieDetailDb")
     fun getMovieDetailGenres(): Single<List<DetailMovieAndGenres>>

    @Transaction
    @Query("SELECT * FROM MovieDetailDb WHERE movieDetailId = :detailCode")
     fun loadByDetailId(detailCode: Long): DetailMovieAndGenres
}