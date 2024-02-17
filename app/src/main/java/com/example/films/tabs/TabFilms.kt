package com.example.films.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.DataBase
import com.example.films.databinding.TabFilmsBinding
import com.example.films.models.FilmModel
import com.example.films.repositories.FilmRepository
import com.example.films.viewModels.FilmFactory
import com.example.films.viewModels.FilmViewModel


class TabFilms : Fragment() {

    private var binding: TabFilmsBinding? = null
    private var filmRepository: FilmRepository? = null
    private var filmViewModel: FilmViewModel? = null
    private var filmFactory: FilmFactory? = null
    private var filmAdapter: FilmAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabFilmsBinding.inflate(inflater, container, false)
        val filmDao = DataBase.getInstance((context as FragmentActivity).application).filmDao
        filmRepository = FilmRepository(filmDao)
        filmFactory = FilmFactory(filmRepository!!)
        filmViewModel = ViewModelProvider(this, filmFactory!!).get(FilmViewModel::class.java)

        initRecyclerFilms()

        binding?.deleteAllFilms?.setOnClickListener(View.OnClickListener {
            filmViewModel?.deleteAllFilms()
        })
        return binding?.root
    }

    private fun initRecyclerFilms(){
        binding?.recyclerFilms?.layoutManager = LinearLayoutManager(context)
        filmAdapter = FilmAdapter({filmModel: FilmModel -> deleteFilm(filmModel)  },
            {filmModel: FilmModel -> editFilm(filmModel)  })
        binding?.recyclerFilms?.adapter = filmAdapter

        displayFilm()
    }

    private fun displayFilm(){
        filmViewModel?.films?.observe(viewLifecycleOwner, Observer {
            filmAdapter?.setList(it)
            filmAdapter?.notifyDataSetChanged()
        })
    }

    private fun deleteFilm(filmModel: FilmModel){
        filmViewModel?.deleteFilm(filmModel)
    }

    private fun editFilm(filmModel: FilmModel){
        val panelEditFilm = PanelEditFilm()
        val parameters = Bundle()
        parameters.putString("idFilm", filmModel.id.toString())
        parameters.putString("nameFilm", filmModel.name)
        parameters.putString("categoryFilm", filmModel.category)
        parameters.putString("timeFilm", filmModel.filmTime)
        parameters.putString("infoFilm", filmModel.info)
        panelEditFilm.arguments = parameters

        panelEditFilm.show((context as FragmentActivity).supportFragmentManager, "editFilm")
    }


}