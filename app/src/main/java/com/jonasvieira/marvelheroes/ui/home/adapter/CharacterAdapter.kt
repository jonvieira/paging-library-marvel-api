package com.jonasvieira.marvelheroes.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonasvieira.marvelheroes.R
import com.jonasvieira.marvelheroes.extensions.load
import com.jonasvieira.marvelheroes.model.Character
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val items = mutableListOf<Character>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_character, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val character = items[p1]
        p0.txtName.text = character.name
        p0.imgThumbnail.load("${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
    }

    fun add(character: Character) {
        items.add(character)
        notifyItemInserted(items.lastIndex)
    }
}