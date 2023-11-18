package com.example.randommovie.data.repository

import android.content.res.Resources
import android.util.Log
import com.example.randommovie.R
import com.example.randommovie.data.dto.MovieDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class MoviesRepository(private val resources: Resources) {

    fun getMovies(): List<MovieDto> {
        val inputStream: InputStream = resources.openRawResource(R.raw.movies_data)
        return try {
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            val jsonString = String(buffer, Charsets.UTF_8)

            val gson = Gson()
            val movieListType = object : TypeToken<List<MovieDto>>() {}.type
            gson.fromJson(jsonString, movieListType)

        } catch (e: IOException) {
            Log.e("MainActivity", "Error loading JSON from raw resources", e)
            emptyList()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                Log.e("MainActivity", "Error closing InputStream", e)
            }
        }
    }
}