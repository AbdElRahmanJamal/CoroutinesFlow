package com.coroutinesflow.frameworks

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.coroutinesflow.R

fun downloadImage(context: Context, url: String, imageView: ImageView) {

    Glide.with(context).load(url).placeholder(R.drawable.ic_the_avengers)
        .error(R.drawable.ic_marvel_logo)
        .fitCenter()
        .into(imageView)

}