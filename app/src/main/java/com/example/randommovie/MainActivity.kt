package com.example.randommovie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.randommovie.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: MutableList<Movie>
    private lateinit var binding: ActivityMainBinding

    data class Movie(
        val title: String,
        val director: String,
        val year: Int,
        val genre: List<String>,
        val actors: List<String>,
        val rating: Double
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateMovieList()

        binding.nextMovieButton.setOnClickListener { showRandomMovie() }
        binding.resetButton.setOnClickListener { resetMovies() }
    }

    private fun showRandomMovie() {
        if (movieList.isEmpty()) {
            resetMovieInfo()
            binding.movieTitleTextView.text = getString(R.string.allMoviesViewed)
        } else {
            val randomMovie = movieList.removeAt(0)
            bindMovieInfo(randomMovie)
        }
    }

    private fun bindMovieInfo(movie: Movie) {
        with(binding) {
            movieTitleTextView.text = movie.title
            movieDirectorTextView.text = movie.director
            movieYearTextView.text = movie.year.toString()
            movieGenreTextView.text = movie.genre.joinToString()
            movieActorsTextView.text = movie.actors.joinToString()
            movieRatingTextView.text = movie.rating.toString()
        }
    }

    private fun resetMovieInfo() {
        with(binding) {
            movieTitleTextView.text = ""
            movieDirectorTextView.text = ""
            movieYearTextView.text = ""
            movieGenreTextView.text = ""
            movieActorsTextView.text = ""
            movieRatingTextView.text = ""
        }
    }

    private fun resetMovies() {
        updateMovieList()
        resetMovieInfo()
        binding.movieTitleTextView.text = getString(R.string.movieTextView)
    }

    private fun updateMovieList() {
        val json = loadJsonFromRaw()
        if (json.isNotEmpty()) {
            Log.d("MainActivity", "JSON loaded successfully")
            movieList = json.toMutableList()
            movieList.shuffle()
        } else {
            Log.e("MainActivity", "Failed to load JSON")
        }
    }

    private fun loadJsonFromRaw(): List<Movie> {
        val inputStream: InputStream = resources.openRawResource(R.raw.movies_data)
        return try {
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            val jsonString = String(buffer, Charsets.UTF_8)

            val gson = Gson()
            val movieListType = object : TypeToken<List<Movie>>() {}.type
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
