package com.vickydroid.productshowcase.network

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vickydroid.productshowcase.model.ImagePath
import java.lang.reflect.Type

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        val mapType: Type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return gson.toJson(map)
    }

    // Existing List<String> converters
    @TypeConverter
    fun fromListString(value: String?): List<String>? {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromImagePath(imagePath: ImagePath?): String? {
        return Gson().toJson(imagePath)
    }

    @TypeConverter
    fun toImagePath(imagePathString: String?): ImagePath? {
        return Gson().fromJson(imagePathString, object : TypeToken<ImagePath?>() {}.type)
    }
}
