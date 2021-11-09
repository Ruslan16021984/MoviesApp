package ru.androidschool.intensiv.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieDb::class, MovieDetailDb::class, GenreDb::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class TheMovieDatabase: RoomDatabase() {
    abstract fun movieDao(): TheMovieDbDao
    abstract fun movieDetailDao(): MovieDetailDao
    companion object{
        private const val DB_NAME = "TheMovieDatabase"
        private var db: TheMovieDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): TheMovieDatabase{
            synchronized(LOCK){
                db?.let { return it }
               val instance = Room.databaseBuilder(context,
               TheMovieDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }

        }
    }
}