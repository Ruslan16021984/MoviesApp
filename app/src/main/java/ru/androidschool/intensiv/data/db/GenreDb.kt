package ru.androidschool.intensiv.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GenreDb")
data class GenreDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val ownerId: Int
)