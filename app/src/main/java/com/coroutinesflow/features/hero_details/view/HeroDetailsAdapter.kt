package com.coroutinesflow.features.hero_details.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coroutinesflow.R
import com.coroutinesflow.features.heroes_home.data.entities.Results
import com.coroutinesflow.frameworks.downloadImage
import kotlinx.android.synthetic.main.marvel_hero_details_row_item.view.*

class HeroDetailsAdapter :
    RecyclerView.Adapter<HeroDetailsAdapter.MarvelHeroCharacterViewHolder>() {

    private var marvelCharacterSectionList: MutableList<Results> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelHeroCharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.marvel_hero_details_row_item, parent, false)
        return MarvelHeroCharacterViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarvelHeroCharacterViewHolder, position: Int) {
        val marvelCharacter = marvelCharacterSectionList[position]
        marvelCharacter.thumbnail?.let {
            val imageURL =
                marvelCharacter.thumbnail.path + "." + marvelCharacter.thumbnail.extension
            downloadImage(imageURL, holder.charactersImage)
            holder.charactersName.text = marvelCharacter.name ?: marvelCharacter.title
        }
    }


    internal fun setMarvelHeroCharacterSectionList(marvelCharacters: List<Results>) {
        this.marvelCharacterSectionList.clear()
        this.marvelCharacterSectionList.addAll(marvelCharacters)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return marvelCharacterSectionList.size
    }

    inner class MarvelHeroCharacterViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val charactersImage: ImageView = itemView.character_image_details_page
        val charactersName: TextView = itemView.character_name_details_page

    }
}

