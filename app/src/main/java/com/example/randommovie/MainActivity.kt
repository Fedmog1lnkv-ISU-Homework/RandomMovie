package com.example.randommovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randommovie.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: Array<String>
    private lateinit var displayedMovies: MutableList<String>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieList = resources.getStringArray(R.array.movie_list)
        displayedMovies = mutableListOf()

        binding.nextMovieButton.setOnClickListener { showRandomMovie() }
        binding.resetButton.setOnClickListener { resetMovies() }
    }

    private fun showRandomMovie() {
        if (displayedMovies.size == movieList.size) {
            binding.movieTextView.text = "Все фильмы просмотрены"
        } else {
            var randomMovie: String
            do {
                randomMovie = movieList.random()
            } while (displayedMovies.contains(randomMovie))

            displayedMovies.add(randomMovie)
            binding.movieTextView.text = randomMovie
        }
    }

    private fun resetMovies() {
        displayedMovies.clear()
        binding.movieTextView.text = "Нажмите кнопку для получения фильма"
    }
}
