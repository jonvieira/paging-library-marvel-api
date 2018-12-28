package com.jonasvieira.marvelheroes.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.jonasvieira.marvelheroes.R
import com.jonasvieira.marvelheroes.ui.home.adapter.CharacterAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter()
    }

    private var recyclerState: Parcelable? = null

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
        recyclerCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == adapter.itemCount - 1 && !viewModel.isLoading) {
                    loadCharacter(viewModel.currentPage + 1)
                }
            }
        })
        loadCharacter(viewModel.currentPage + 1)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable("lmState", recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun loadCharacter(page: Int) {
        val disposable = viewModel.load(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { character ->
                    adapter.add(character)
                },
                { error ->
                    Log.e("", "error", error)
                },
                {
                    if (recyclerState != null) {
                        recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                }
            )
    }
}
