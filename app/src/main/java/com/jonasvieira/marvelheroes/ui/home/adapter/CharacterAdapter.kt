package com.jonasvieira.marvelheroes.ui.home.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonasvieira.marvelheroes.R
import com.jonasvieira.marvelheroes.extensions.load
import com.jonasvieira.marvelheroes.model.Character
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter : PagedListAdapter<Character, CharacterAdapter.ViewHolder>(characterDiff) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_character, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.txtName.text = character?.name
        holder.imgThumbnail.load("${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(old: Character, new: Character): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Character, new: Character): Boolean {
                return old == new
            }
        }
    }
}
