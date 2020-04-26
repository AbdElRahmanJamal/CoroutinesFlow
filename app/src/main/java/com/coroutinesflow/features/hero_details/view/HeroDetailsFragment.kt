package com.coroutinesflow.features.hero_details.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coroutinesflow.*
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.base.view.BaseScreenFragment
import com.coroutinesflow.features.hero_details.data.di.MarvelHeroDetailsDependencyInjection.heroDetailsViewModelFactoryObject
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsSectionsID
import com.coroutinesflow.frameworks.network.apiFactory
import kotlinx.android.synthetic.main.hero_details_fragment.*
import kotlinx.android.synthetic.main.marvel_page_details_section.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin

class HeroDetailsFragment : BaseScreenFragment() {

    private lateinit var heroModel: Results
    private val heroDetailsAdapterComics = HeroDetailsAdapter()
    private val heroDetailsAdapterSeries = HeroDetailsAdapter()
    private val heroDetailsAdapterStories = HeroDetailsAdapter()
    private val heroDetailsAdapterEvents = HeroDetailsAdapter()

    override fun getLayoutId() = R.layout.hero_details_fragment

    override fun getScreenTitle() = ""

    override fun startKoinDependancyInjection() = startKoin(
        context!!.applicationContext, listOf(apiFactory, heroDetailsViewModelFactoryObject)
    )

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let { bundle ->
            heroModel = HeroDetailsFragmentArgs.fromBundle(bundle).marvelHero

            showScreenContent()
            initComicsRecView()
            initSeriesRecView()
            initStoriesRecView()
            initEventsRecView()

            val factory: HeroDetailsViewModelFactory = get()

            with(ViewModelProvider(this, factory).get(HeroDetailsViewModel::class.java)) {


                getHeroSectionListDetails(COMICS_API, HeroDetailsSectionsID.COMICS, heroModel.id)
                    .observe(viewLifecycleOwner, Observer {
                        comics.section_title.text = HeroDetailsSectionsID.COMICS.name
                        handleSectionStates(COMICS_API, it, comics)
                    })

                getHeroSectionListDetails(SERIES_API, HeroDetailsSectionsID.SERIES, heroModel.id)
                    .observe(viewLifecycleOwner, Observer {
                        series.section_title.text = HeroDetailsSectionsID.SERIES.name
                        handleSectionStates(SERIES_API, it, series)
                    })

                getHeroSectionListDetails(STORIES_API, HeroDetailsSectionsID.STORIES, heroModel.id)
                    .observe(viewLifecycleOwner, Observer {
                        stories.section_title.text = HeroDetailsSectionsID.STORIES.name
                        handleSectionStates(STORIES_API, it, stories)
                    })

                getHeroSectionListDetails(EVENTS_API, HeroDetailsSectionsID.EVENTS, heroModel.id)
                    .observe(viewLifecycleOwner, Observer {
                        events.section_title.text = HeroDetailsSectionsID.EVENTS.name
                        handleSectionStates(EVENTS_API, it, events)
                    })

            }
        }
    }

    private fun initComicsRecView() {
        comics.marvel_character_section_recView.apply {
            layoutManager = LinearLayoutManager(
                this@HeroDetailsFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = heroDetailsAdapterComics
        }
    }

    private fun initStoriesRecView() {
        stories.marvel_character_section_recView.apply {
            layoutManager = LinearLayoutManager(
                this@HeroDetailsFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = heroDetailsAdapterStories
        }
    }

    private fun initSeriesRecView() {
        series.marvel_character_section_recView.apply {
            layoutManager = LinearLayoutManager(
                this@HeroDetailsFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = heroDetailsAdapterSeries
        }
    }

    private fun initEventsRecView() {
        events.marvel_character_section_recView.apply {
            layoutManager = LinearLayoutManager(
                this@HeroDetailsFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = heroDetailsAdapterEvents
        }
    }

    private fun handleSectionStates(
        apiID: String,
        state: APIState<List<Results>>,
        view: View
    ) {
        when (state) {
            is APIState.LoadingState -> view.loading_indicator.visibility = View.VISIBLE

            is APIState.ErrorState -> {
                view.loading_indicator.visibility = View.GONE
                showErrorContent(state)
            }

            is APIState.DataStat -> {
                view.loading_indicator.visibility = View.GONE
                view.marvel_character_section_recView.visibility = View.VISIBLE
                val marvelHeroesAdapter =
                    view.marvel_character_section_recView.adapter as HeroDetailsAdapter
                marvelHeroesAdapter.setMarvelHeroCharacterSectionList(state.value)
            }
        }
    }
}
