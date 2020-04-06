package com.coroutinesflow.frameworks

import android.widget.ImageView
import com.coroutinesflow.R
import com.squareup.picasso.Picasso

fun downloadImage(url: String, imageView: ImageView) {

    val get = Picasso
        .get()
    get.setIndicatorsEnabled(true)
    get.load(url)
        .error(R.drawable.ic_marvel_logo)
        .fit()
        .placeholder(R.drawable.ic_the_avengers)
        .into(imageView)
}