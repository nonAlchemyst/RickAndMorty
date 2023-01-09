package com.example.rickandmorty.presentation.presenters.common

fun getIdsFromUrls(urls: List<String>): String{
    val ids = urls.map { url ->
        val index = url.lastIndexOf("/")
        if(index + 1 <= url.lastIndex)
            url.substring(index + 1)
        else
            ""
    }
    return ids.toString1()
}

fun<T> List<T>.toString1(): String{
    val output = java.lang.StringBuilder()
    val lastIndex = this.lastIndex
    this.forEachIndexed { index, t ->
        output.append(t)
        if(index != lastIndex)
            output.append(",")
    }
    return output.toString()
}