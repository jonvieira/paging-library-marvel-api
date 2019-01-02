package com.jonasvieira.marvelheroes.ui.home

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.jonasvieira.marvelheroes.ui.home.paging.CharacterDataSourceFactory
import com.jonasvieira.marvelheroes.model.Character
import com.jonasvieira.marvelheroes.services.MarvelApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    var characterList: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pagedSize = 20

    private val sourceFactory: CharacterDataSourceFactory

    init {
        sourceFactory = CharacterDataSourceFactory(
            compositeDisposable,
            MarvelApi.getService()
        )

        val config = PagedList.Config.Builder()
            .setPageSize(pagedSize)
            .setInitialLoadSizeHint(pagedSize * 3)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}