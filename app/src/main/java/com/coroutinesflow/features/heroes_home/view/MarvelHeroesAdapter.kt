package com.coroutinesflow.features.heroes_home.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coroutinesflow.R
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHeroesUIModel
import com.coroutinesflow.frameworks.downloadImage
import kotlinx.android.synthetic.main.marvel_hero_item.view.*

class MarvelHeroesAdapter :
    RecyclerView.Adapter<MarvelHeroesAdapter.MarvelCharactersViewHolder>() {

    private var marvelHeroesUIModel: MarvelHeroesUIModel = MarvelHeroesUIModel(mutableListOf())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.marvel_hero_item, parent, false)
        return MarvelCharactersViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
        val marvelCharacter = marvelHeroesUIModel.results[position]
        marvelCharacter.thumbnail?.let {
            val imageURL =
                marvelCharacter.thumbnail.path + "." + marvelCharacter.thumbnail.extension
            imageURL.replace("http", "https")
            downloadImage(imageURL, holder.charactersImage)
            holder.charactersName.text = marvelCharacter.name ?: marvelCharacter.title
            holder.itemView.setOnClickListener {
                marvelHeroesUIModel.onMarvelHeroClicked?.let { it1 -> it1(marvelCharacter) }
            }
        }
    }

    internal fun setMarvelCharacters(marvelCharacters: MarvelHeroesUIModel) {
        this.marvelHeroesUIModel.onMarvelHeroClicked = marvelCharacters.onMarvelHeroClicked
        this.marvelHeroesUIModel.results.clear()
        this.marvelHeroesUIModel.results.addAll(marvelCharacters.results)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return marvelHeroesUIModel.results.size
    }

    inner class MarvelCharactersViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val charactersImage: ImageView = itemView.character_image
        val charactersName: TextView = itemView.character_name

    }
}

