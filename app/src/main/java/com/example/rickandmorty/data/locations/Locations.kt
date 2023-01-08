package com.example.rickandmorty.data.locations

import com.example.rickandmorty.data.Info
import com.google.gson.annotations.SerializedName

data class Locations(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Location>
)
