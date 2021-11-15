package ru.androidschool.intensiv.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
data class DetailMovieAndGenres (
    @Embedded
    val movieDetail: MovieDetailDb,
    @Relation(
        parentColumn = "movieDetailId",
        entityColumn = "ownerId"
    )
    val genre: List<GenreDb>
)