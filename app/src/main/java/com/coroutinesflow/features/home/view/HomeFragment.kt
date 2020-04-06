package com.coroutinesflow.features.home.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coroutinesflow.R
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.view.BaseScreenFragment
import com.coroutinesflow.features.home.data.MarvelHomeLocalDataStore
import com.coroutinesflow.features.home.data.MarvelHomeRemoteDataStore
import com.coroutinesflow.features.home.data.MarvelHomeRepository
import com.coroutinesflow.features.home.model.Results
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.get

class HomeFragment : BaseScreenFragment() {

    override fun getLayoutId() = R.layout.home_fragment
    override fun getScreenTitle(): String = ""

    private lateinit var marvelHeroesAdapter: MarvelHeroesAdapter

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecView()
        val factory = HomeViewModelFactory(
            MarvelHomeRepository(
                MarvelHomeRemoteDataStore(get()),
                MarvelHomeLocalDataStore()
            )
        )
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
        marvelHeroesAdapter.setMarvelCharacters(results)
    }

    private fun initRecView() {
        marvelHeroesAdapter = MarvelHeroesAdapter()
        marvel_character_recView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = marvelHeroesAdapter
        }
    }

}
