package com.example.randommovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randommovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: MutableList<String>
    private lateinit var binding: ActivityMainBinding

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
            binding.movieTextView.text = getString(R.string.allMoviesViewed)
        } else {
            val randomMovie = movieList.removeAt(0)
            binding.movieTextView.text = randomMovie
        }
    }

    private fun resetMovies() {
        updateMovieList()
        binding.movieTextView.text = getString(R.string.movieTextView)
    }

    private fun updateMovieList() {
        movieList = resources.getStringArray(R.array.movie_list).toMutableList()
        movieList.shuffle()
    }
}
