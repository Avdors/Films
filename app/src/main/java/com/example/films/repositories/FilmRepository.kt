package com.example.films.repositories

import androidx.lifecycle.LiveData
import com.example.films.data.CategoryDao
import com.example.films.data.FilmDao
import com.example.films.models.FilmModel

class FilmRepository(private val filmDao: FilmDao) {
        val film = filmDao.getAllFilms()

    fun getFilter(): LiveData<List<FilmModel>>{
        return filmDao.getFilter()
    }
    suspend fun insertFilm(filmModel: FilmModel){
        filmDao.insertFilm(filmModel)
    }
    suspend fun updateFilm(filmModel: FilmModel){
        filmDao.updateFilm(filmModel)
    }
    suspend fun deleteFilm(filmModel: FilmModel){
        filmDao.deleteFilm(filmModel)
    }
    suspend fun deleteAllFilms(){
        filmDao.deleteAllFilms()
    }

}