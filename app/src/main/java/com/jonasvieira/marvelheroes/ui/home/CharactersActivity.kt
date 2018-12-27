package com.jonasvieira.marvelheroes.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.jonasvieira.marvelheroes.R
import com.jonasvieira.marvelheroes.ui.home.adapter.CharacterAdapter
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        initRecycler()
    }

    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)

        recyclerCharacters.layoutManager = linearLayoutManager
        recyclerCharacters.hasFixedSize()
        recyclerCharacters.adapter = adapter
    }
}
