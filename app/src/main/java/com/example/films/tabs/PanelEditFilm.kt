package com.example.films.tabs

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.films.R
import com.example.films.data.DataBase
import com.example.films.databinding.PanelEditFilmBinding
import com.example.films.models.CategoryModel
import com.example.films.repositories.CategoryRepository
import com.example.films.repositories.FilmRepository
import com.example.films.viewModels.FilmFactory
import com.example.films.viewModels.FilmViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PanelEditFilm : BottomSheetDialogFragment(), View.OnKeyListener, View.OnClickListener{
    private var binding: PanelEditFilmBinding? = null
    private var filmRepository: FilmRepository? = null
    private var filmViewModel: FilmViewModel? = null
    private var filmFactory: FilmFactory? = null
    private var idFilm: Int? = null
    private var categoryRepository: CategoryRepository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PanelEditFilmBinding.inflate(inflater, container,false)

        idFilm = arguments?.getString("idFilm")?.toInt()
        binding?.editNameFilm?.setText(arguments?.getString("nameFilm").toString())
        binding?.editTimeFilm?.setText(arguments?.getString("timeFilm").toString())
        binding?.editInfoFilm?.setText(arguments?.getString("infoFilm").toString())


        val filmDao = DataBase.getInstance((context as FragmentActivity).application).filmDao
        filmRepository = FilmRepository(filmDao)
        filmFactory = FilmFactory(filmRepository!!)
        filmViewModel = ViewModelProvider(this, filmFactory!!).get(FilmViewModel::class.java)

        val categoryDao = DataBase.getInstance((context as FragmentActivity).application).categoryDao
        categoryRepository = CategoryRepository(categoryDao)



        binding?.editNameFilm?.setOnKeyListener(this)
        binding?.editTimeFilm?.setOnKeyListener(this)
        binding?.editInfoFilm?.setOnKeyListener(this)

        binding?.buttonEditFilm?.setOnClickListener(this)
        binding?.buttonEditCategory?.setOnClickListener(this)
        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
       when(view.id){
           R.id.editNameFilm -> {
               if(keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){

                   binding?.resEditNameFilm?.text = binding?.editNameFilm?.text
                   binding?.editNameFilm?.setText("")
                   return true
               }

           }
           R.id.editTimeFilm -> {
               if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                   binding?.resEditTimeFilm?.text = binding?.editTimeFilm?.text
                   binding?.editTimeFilm?.setText("")
                   return true
               }
           }


       }
        return false
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonEditFilm ->{
                filmViewModel?.startUpdateFilm(idFilm.toString().toInt(), binding?.resEditNameFilm?.text?.toString()!!,
                    binding?.resEditCategoryFilm?.text?.toString()!!, binding?.resEditTimeFilm?.text?.toString()!!,
                    binding?.editInfoFilm?.text?.toString()!!)

                dismiss()

                (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, TabFilms()).commit()
            }

            R.id.buttonEditCategory ->{


            }


        }
    }




}