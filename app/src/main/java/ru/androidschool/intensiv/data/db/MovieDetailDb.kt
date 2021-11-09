package ru.androidschool.intensiv.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetailDb")
data class MovieDetailDb (
    @PrimaryKey
    @ColumnInfo(name = "movieDetailId")
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val homepage: String,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)