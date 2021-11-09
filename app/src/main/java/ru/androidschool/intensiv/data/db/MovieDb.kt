package ru.androidschool.intensiv.data.db

import androidx.room.*

@Entity(tableName = "MovieDb")
data class MovieDb (
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Long,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
){
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}