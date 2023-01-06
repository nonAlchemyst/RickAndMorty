package com.example.rickandmorty.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {

    companion object {
        //Общая ссылка для всех запросов
        private val URL = "https://rickandmortyapi.com/api/"
    }

    //Это просто запомните
    //если интересно, то погуглите на досуге
    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Вообще обычно я делаю обобщённый класс
    //чтобы можно было разные интерфейсы с api использовать
    //но и так сойдёт :D
    fun build(): Service = builder.create(Service::class.java)

}