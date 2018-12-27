package com.jonasvieira.marvelheroes.model

data class Response(
    val code: Int,
    val etag: String,
    val data: Data
)