package com.jonasvieira.marvelheroes.ui.home

import android.arch.lifecycle.ViewModel
import com.jonasvieira.marvelheroes.model.Character
import com.jonasvieira.marvelheroes.services.MarvelApi
import io.reactivex.Observable

class CharactersViewModel : ViewModel() {

    var isLoading: Boolean = false
        private set

    var currentPage = -1
        private set

    private val characters = mutableListOf<Character>()

    fun load(page: Int): Observable<Character> {
        isLoading = true

        return if (page <= currentPage) {
            Observable.fromIterable(characters)
        } else {
            currentPage = page
            MarvelApi.getService().allCharacters(page * 20)
                .flatMapIterable { response ->
                    response.data.results
                }
                .doOnNext { c ->
                    characters.add(c)
                    Observable.just(c)
                }
        }.doOnComplete {
            isLoading = false
        }
    }

    fun reset() {
        isLoading = false
        currentPage = -1
        characters.clear()
    }
}