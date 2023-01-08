package com.example.rickandmorty.data.episodes

import com.example.rickandmorty.data.Info
import com.google.gson.annotations.SerializedName

data class Episodes(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Episode>
)