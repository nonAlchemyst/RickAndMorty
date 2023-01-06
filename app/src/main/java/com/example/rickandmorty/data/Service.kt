package com.example.rickandmorty.data

import com.example.rickandmorty.data.characters.Characters
import com.example.rickandmorty.data.characters.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Тут описываем методы нашей api
//Их и будем вызывать для получения данных из сети
interface Service {

    @GET("character")
    fun getAllCharacters(): Call<Characters>

    @GET("character/{id}")
    fun getSingleCharacter(@Path("id") id: Int): Call<Character>

    @GET("character")
    fun getFilteredCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): Call<Characters>

}