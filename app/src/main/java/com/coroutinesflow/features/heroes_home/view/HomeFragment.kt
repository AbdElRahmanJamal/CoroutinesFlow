package com.coroutinesflow.features.heroes_home.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.coroutinesflow.HOME_API
import com.coroutinesflow.R
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.base.view.BaseScreenFragment
import com.coroutinesflow.features.heroes_home.data.di.MarvelHomeHeroesDependencyInjection.homeViewModelFactoryObject
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHeroesUIModel
import com.coroutinesflow.frameworks.network.apiFactory
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin

const val HOME = "HOME"

class HomeFragment : BaseScreenFragment() {

    override fun getLayoutId() = R.layout.home_fragment

    override fun getScreenTitle(): String = ""

    override fun startKoinDependancyInjection() =
        startKoin(context!!.applicationContext, listOf(apiFactory, homeViewModelFactoryObject))

    private lateinit var marvelHeroesAdapter: MarvelHeroesAdapter

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecView()
        val factory: HomeViewModelFactory = get()

        with(ViewModelProvider(this, factory).get(HomeViewModel::class.java)) {
            getListOfMarvelHeroesCharacters(
                limit = 15,
                offset = 0,
                homeID = HOME,
                apiID = HOME_API
            ).observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        is APIState.LoadingState -> setLoadingIndicatorVisibility(View.VISIBLE)
                        is APIState.DataStat -> showContent(it.value)
                        is APIState.ErrorState -> showErrorContent(it)
                    }
                })

        }
    }

    private fun showContent(results: List<Results>) {
        super.showScreenContent()
        val marvelHeroesUIModel = MarvelHeroesUIModel(results as MutableList<Results>, null)
        marvelHeroesAdapter.setMarvelCharacters(marvelHeroesUIModel.apply {
            onMarvelHeroClicked = {
                goToHereDetailsPage(it)
            }
        })
    }

    private fun goToHereDetailsPage(hero: Results) {
        view?.let {
            val goToDetails =
                HomeFragmentDirections.actionHomeFragmentToHeroDetailsFragment(hero)
            Navigation.findNavController(it).navigate(goToDetails)
        }
    }

    private fun initRecView() {
        marvelHeroesAdapter = MarvelHeroesAdapter()
        marvel_character_recView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = marvelHeroesAdapter
        }
    }

}
