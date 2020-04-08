package com.coroutinesflow.features.hero_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.coroutinesflow.R
import com.coroutinesflow.base.view.BaseScreenFragment

class HeroDetailsFragment : BaseScreenFragment() {


    private lateinit var viewModel: HeroDetailsViewModel

    override fun getLayoutId() = R.layout.hero_details_fragment

    override fun getScreenTitle() = ""

    override fun startKoinDependancyInjection() {

    }

    override fun stopKoinDependancyInjection() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HeroDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
