package com.example.rickandmorty.data

import com.example.rickandmorty.data.characters.Characters
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.data.locations.Location
import com.example.rickandmorty.data.locations.Locations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Тут описываем методы нашей api
//Их и будем вызывать для получения данных из сети
interface Service {

    @GET("character")
    fun getAllCharacters(@Query("page") page: Int): Call<Characters>

    @GET("character/{id}")
    fun getSingleCharacter(@Path("id") id: Int): Call<Character>

    @GET("character/{ids}")
    fun getMultipleCharacters(@Path("ids") ids: String): Call<List<Character>>

    @GET("character")
    fun getFilteredCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Call<Characters>

    @GET("location")
    fun getAllLocations(@Query("page") page: Int): Call<Locations>

    @GET("location")
    fun getFilteredLocationsByName(@Query("name") name: String): Call<Locations>

    @GET("location/{id}")
    fun getSingleLocation(@Path("id") id: Int): Call<Location>

}