package com.example.rickandmorty.data.characters

import com.example.rickandmorty.data.Info
import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Character>
)
