package com.example.rickandmorty.data.characters

import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Character>
){
    data class Info(
        @SerializedName("count") val count: Int,
        @SerializedName("pages") val pages: Int,
        @SerializedName("next") val next: String?,
        @SerializedName("prev") val prev: String?
    )
}
