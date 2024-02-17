package com.example.films.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.films.models.CategoryModel
import com.example.films.models.FilmModel

@Database(entities = [CategoryModel::class, FilmModel::class], version = 2)
abstract class DataBase: RoomDatabase() {
    abstract val filmDao : FilmDao
    abstract val categoryDao : CategoryDao

    companion object{
    @Volatile
    private var INSTANSE : com.example.films.data.DataBase? = null
    fun getInstance(context: Context):com.example.films.data.DataBase{
        synchronized(this){
            var instance = INSTANSE
            if(instance==null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.films.data.DataBase::class.java,
                    "database"
                ).build()
            }
            return instance
        }
    }
    }
}