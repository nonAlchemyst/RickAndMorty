package com.example.rickandmorty.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    //Общая ссылка для всех запросов
    private val URL = "https://rickandmortyapi.com/api/"

    //Это просто запомните
    //если интересно, то погуглите на досуге
    val service = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

    //Вообще обычно я делаю обобщённый класс
    //чтобы можно было разные интерфейсы с api использовать
    //но и так сойдёт :D
    //fun build(): Service = builder.create(Service::class.java)

}