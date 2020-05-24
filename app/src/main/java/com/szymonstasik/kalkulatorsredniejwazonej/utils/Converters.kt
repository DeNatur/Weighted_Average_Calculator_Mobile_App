package com.szymonstasik.kalkulatorsredniejwazonej.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<Float>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Float>? {

        val objects = Gson().fromJson(value, Array<Float>::class.java) as Array<Float>
        val list = objects.toList()
        return list
    }
}