package com.example.randommovie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.randommovie.data.dto.MovieDto
import com.example.randommovie.data.repository.MoviesRepository
import com.example.randommovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: MutableList<MovieDto>
    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesRepository: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesRepository = MoviesRepository(resources)

        updateMovieList()

        binding.nextMovieButton.setOnClickListener { showRandomMovie() }
        binding.resetButton.setOnClickListener { resetMovies() }
    }

    private fun showRandomMovie() {
        if (movieList.isEmpty()) {
            bindMovieInfo(null)
            binding.movieTitleTextView.text = getString(R.string.allMoviesViewed)
        } else {
            val randomMovie = movieList.removeAt(0)
            bindMovieInfo(randomMovie)
        }
    }

    private fun bindMovieInfo(movie: MovieDto?) {
        with(binding) {
            movieTitleTextView.text = movie?.title ?: ""
            movieDirectorTextView.text = movie?.director ?: ""
            movieYearTextView.text = movie?.year?.toString() ?: ""
            movieGenreTextView.text = movie?.genre?.joinToString() ?: ""
            movieActorsTextView.text = movie?.actors?.joinToString() ?: ""
            movieRatingTextView.text = movie?.rating?.toString() ?: ""
        }
    }

    private fun resetMovies() {
        updateMovieList()
        bindMovieInfo(null)
        binding.movieTitleTextView.text = getString(R.string.movieTextView)
    }

    private fun updateMovieList() {
        val movies = moviesRepository.getMovies()
        if (movies.isNotEmpty()) {
            Log.d("MainActivity", "Movies loaded successfully")
            movieList = movies.shuffled().toMutableList()
        } else {
            Log.e("MainActivity", "Failed to load movies")
        }
    }
}
