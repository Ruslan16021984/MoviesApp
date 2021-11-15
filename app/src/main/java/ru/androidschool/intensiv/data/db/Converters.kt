package ru.androidschool.intensiv.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: List<Int>?): String? {
        return (if (value == null) null else Gson().toJson(value))
    }

    @TypeConverter
    fun listFromJson(value: String?) = Gson().fromJson(value, Array<Int>::class.java).asList()
}