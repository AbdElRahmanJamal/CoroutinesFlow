package com.coroutinesflow.features.home.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coroutinesflow.R
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.view.BaseScreenFragment
import com.coroutinesflow.features.home.data.di.MarvelHomeHeroesDependencyInjection.homeViewModelFactoryObject
import com.coroutinesflow.features.home.model.MarvelHeroesUIModel
import com.coroutinesflow.features.home.model.Results
import com.coroutinesflow.frameworks.network.apiFactory
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class HomeFragment : BaseScreenFragment() {

    override fun getLayoutId() = R.layout.home_fragment

    override fun getScreenTitle(): String = ""

    override fun startKoinDependancyInjection() =
        startKoin(context!!.applicationContext, listOf(apiFactory, homeViewModelFactoryObject))

    override fun stopKoinDependancyInjection() = stopKoin()

    private lateinit var marvelHeroesAdapter: MarvelHeroesAdapter

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecView()
        val factory: HomeViewModelFactory = get()
        with(ViewModelProvider(this, factory).get(HomeViewModel::class.java)) {
            lifecycleScope.launchWhenCreated {
                getListOfMarvelHeroesCharacters().observeForever {
                    when (it) {
                        is APIState.LoadingState -> setLoadingIndicatorVisibility(View.VISIBLE)
                        is APIState.DataStat -> showContent(it.value.data.results)
                        is APIState.ErrorState -> showErrorContent(it)
                    }
                }
            }
        }
    }

    private fun showContent(results: List<Results>) {
        super.showScreenContent()
        val marvelHeroesUIModel = MarvelHeroesUIModel(results as MutableList<Results>, null)
        marvelHeroesAdapter.setMarvelCharacters(marvelHeroesUIModel.apply {
            onMarvelHeroClicked = {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecView() {
        marvelHeroesAdapter = MarvelHeroesAdapter()
        marvel_character_recView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = marvelHeroesAdapter
        }
    }

}
