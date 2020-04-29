package com.coroutinesflow.features.heroes_home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coroutinesflow.R
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHeroesUIModel
import com.coroutinesflow.frameworks.downloadImage
import kotlinx.android.synthetic.main.marvel_hero_item.view.*


class MarvelHeroesListAdapter :
    ListAdapter<MarvelHeroesUIModel, MarvelHeroesListAdapter.MarvelHeroesViewHolderAdapter>(
        MarvelItemDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): MarvelHeroesViewHolderAdapter {
        return MarvelHeroesViewHolderAdapter(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.marvel_hero_item, parent, false), parent.context
        )
    }

    override fun onBindViewHolder(holder: MarvelHeroesViewHolderAdapter, position: Int) {
        holder.bindTo(getItem(position))
    }

    //view holder
    class MarvelHeroesViewHolderAdapter(itemView: View, val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bindTo(marvelHeroesUIModel: MarvelHeroesUIModel) {
            val heroItem = marvelHeroesUIModel.heroItem
            heroItem.thumbnail?.let {
                val imageURL =
                    heroItem.thumbnail.path + "." + heroItem.thumbnail.extension
                imageURL.replace("http", "https")
                downloadImage(context, imageURL, itemView.character_image)
                itemView.character_name.text = heroItem.name ?: heroItem.title

                itemView.setOnClickListener {
                    marvelHeroesUIModel.onMarvelHeroClicked?.let { it1 -> it1(heroItem) }
                }
            }
        }
    }

    private class MarvelItemDiffCallback : DiffUtil.ItemCallback<MarvelHeroesUIModel>() {
        override fun areItemsTheSame(oldItem: MarvelHeroesUIModel, newItem: MarvelHeroesUIModel) =
            oldItem.heroItem == newItem.heroItem


        override fun areContentsTheSame(
            oldItem: MarvelHeroesUIModel,
            newItem: MarvelHeroesUIModel
        ) =
            oldItem.heroItem == newItem.heroItem
    }
}
