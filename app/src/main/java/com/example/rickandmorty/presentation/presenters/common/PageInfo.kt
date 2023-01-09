package com.example.rickandmorty.presentation.presenters.common

data class PageInfo(
    var num: Int = 1,
    var canNext: Boolean = true
) {
    fun next(){
        num++
        canNext = true
    }

    fun stop(){
        canNext = false
    }
}
