package com.example.films.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.films.repositories.FilmRepository

class FilmFactory constructor(private val filmRepository : FilmRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FilmViewModel::class.java)){
            FilmViewModel(this.filmRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}