package com.jonasvieira.marvelheroes.ui.home.paging

import android.arch.paging.DataSource
import com.jonasvieira.marvelheroes.model.Character
import com.jonasvieira.marvelheroes.services.MarvelApi
import io.reactivex.disposables.CompositeDisposable

class CharacterDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: MarvelApi
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}