package com.coroutinesflow.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.coroutinesflow.R
import com.coroutinesflow.base.data.APIState
import kotlinx.android.synthetic.main.fragment_base_screen.*
import kotlinx.android.synthetic.main.fragment_base_screen.view.*

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseScreenFragment : Fragment() {


    private var fragmentView: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setScreenTitle(getScreenTitle())
        val inflate = inflater.inflate(R.layout.fragment_base_screen, container, false)
        fragmentView = inflater.inflate(getLayoutId(), null, false) as ViewGroup
        inflate.base_fragment_container.addView(fragmentView, 0)
        startKoinDependancyInjection()
        return inflate
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun getScreenTitle(): String
    protected abstract fun startKoinDependancyInjection()
    protected abstract fun stopKoinDependancyInjection()

    protected fun setLoadingIndicatorVisibility(visibility: Int) {
        lottie_loading?.apply {
            this.visibility = visibility
        }
    }

    protected fun showScreenContent() {
        base_fragment_container?.apply {
            this.visibility = View.VISIBLE
        }
        setLoadingIndicatorVisibility(View.GONE)
    }

    protected fun showErrorContent(it: APIState.ErrorState) {
        error_container?.apply {
            this.visibility = View.VISIBLE
        }
        setLoadingIndicatorVisibility(View.GONE)
    }

    private fun setScreenTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopKoinDependancyInjection()
    }
}
