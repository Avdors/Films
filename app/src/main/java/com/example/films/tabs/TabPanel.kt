package com.example.films.tabs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.example.films.R
import com.example.films.data.DataBase
import com.example.films.databinding.TabPanelBinding
import com.example.films.models.CategoryModel
import com.example.films.repositories.CategoryRepository
import com.example.films.repositories.FilmRepository
import com.example.films.viewModels.CategoryFactory
import com.example.films.viewModels.CategoryViewModel
import com.example.films.viewModels.FilmFactory
import com.example.films.viewModels.FilmViewModel


class TabPanel : Fragment(), View.OnClickListener, View.OnKeyListener{
    //View.OnClickListener, View.OnKeyListener, AdapterView.OnItemSelectedListener
    private var binding: TabPanelBinding? = null
    private var categoryRepository: CategoryRepository? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var categoryFactory: CategoryFactory? = null


    private var filmRepository: FilmRepository? = null
    private var filmViewModel: FilmViewModel? = null
    private var filmFactory: FilmFactory? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabPanelBinding.inflate(inflater, container, false)

        val categoryDao = DataBase.getInstance((context as FragmentActivity).application).categoryDao
        categoryRepository = CategoryRepository(categoryDao)
        categoryFactory = CategoryFactory(categoryRepository!!)
        categoryViewModel = ViewModelProvider(this, categoryFactory!!).get(CategoryViewModel::class.java)

        val filmDao = DataBase.getInstance((context as FragmentActivity).application).filmDao
        filmRepository = FilmRepository(filmDao)
        filmFactory = FilmFactory(filmRepository!!)
        filmViewModel = ViewModelProvider(this, filmFactory!!).get(FilmViewModel::class.java)

        binding?.enterNameFilm?.setOnClickListener(this)

        binding?.enterTimeFilm?.setOnClickListener(this)

        binding?.buttonAddFilm?.setOnClickListener(this)

        binding?.buttonAddCategory?.setOnClickListener(this)

        binding?.buttonEditCategory?.setOnClickListener(this)

        return binding?.root
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonAddCategory ->{
                categoryViewModel?.startInsert(binding?.enterNameCategoryadd?.text?.toString()!!)
                binding?.enterNameCategoryadd?.setText("")
            }

            R.id.buttonAddFilm ->{
            filmViewModel?.startInsert(binding?.resEnterNameFilm?.text.toString()!!,
            binding?.resEnterCategoryFilm?.text?.toString()!!,
            binding?.resEnterTimeFilm?.text?.toString()!!,
            binding?.enterInfoFilm?.text?.toString()!!)

            clearResEnterFilm()

            }


        }
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when(view.id){

            R.id.enterNameFilm -> {
                if(keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){
                    binding?.resEnterNameFilm?.text = binding?.enterNameFilm?.text
                    return true
                }
            }
            R.id.enterTimeFilm ->{
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){
                    binding?.enterTimeFilm?.text = binding?.enterTimeFilm?.text
                }
            }
        }

        return false
    }
 private fun clearResEnterFilm(){
    binding?.resEnterNameFilm?.setText("")
    binding?.resEnterCategoryFilm?.setText("")
    binding?.resEnterTimeFilm?.setText("")
    binding?.enterInfoFilm?.setText("")
    binding?.enterNameFilm?.setText("")
    binding?.enterTimeFilm?.setText("")

 }



}