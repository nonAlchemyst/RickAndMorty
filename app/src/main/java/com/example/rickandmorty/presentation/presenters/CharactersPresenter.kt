package com.example.rickandmorty.presentation.presenters

import android.util.Log
import com.example.rickandmorty.data.ServiceBuilder
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.data.locations.Location
import com.example.rickandmorty.data.locations.Locations
import com.example.rickandmorty.presentation.presenters.base.BasePresenter
import retrofit2.Call
import retrofit2.Response

class CharactersPresenter : BasePresenter<CharactersPresenter.CharactersView>() {

    private val service = ServiceBuilder.service
    private var currFilter: String? = null
    private var filters = arrayListOf<Pair<Int, String>>()

    override var view: CharactersView? = null

    override fun attachView(newView: CharactersView) {
        view = newView
    }

    override fun detachView() {
        view = null
    }

    fun onListEnd(){
        loadCharactersByLocationName(currFilter ?: "")
    }

    fun loadFilters(){
        loadAllFilters(1)
    }

    fun onFilterCharacters(filter: String){
        currFilter = filter
        loadCharactersByLocationName(currFilter ?: "")
    }

    fun loadCharactersByLocationName(name: String){
        loadLocationByName(name)
    }

    private fun loadAllFilters(page: Int){
        service.getAllLocations(page).enqueue(object: retrofit2.Callback<Locations>{
            override fun onResponse(call: Call<Locations>, response: Response<Locations>) {
                if(response.code() != 200) //Ошибка загрузки фильтров
                    return
                response.body()?.let {
                    filters.addAll(getFiltersFromLocations(it.results))
                    if(it.info.next != null)
                        loadAllFilters(page+1)
                    else
                        view?.onFiltersLoad(filters.map { it1 -> it1.second })
                }
            }

            override fun onFailure(call: Call<Locations>, t: Throwable) {
                //Ошибка загрузки фильтров
            }
        })
    }

    private fun loadCharacterById(id: String){
        val characterId = id.toIntOrNull() ?: return
        service.getSingleCharacter(characterId).enqueue(object: retrofit2.Callback<Character>{
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if(response.code() != 200) {
                    view?.onCharactersUpdate(emptyList())
                    return
                }
                response.body()?.let {
                    view?.onCharactersUpdate(listOf(it))
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                view?.onToast("Ошибка загрузки персонажа по id")
            }
        })
    }

    private fun loadCharactersByIds(ids: String){
        service.getMultipleCharacters(ids).enqueue(object: retrofit2.Callback<List<Character>>{
            override fun onResponse(call: Call<List<Character>>, response: Response<List<Character>>) {
                if(response.code() != 200) {
                    view?.onCharactersUpdate(emptyList())
                    return
                }
                response.body()?.let {
                    view?.onCharactersUpdate(it)
                }
            }

            override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                //Ошибка
                Log.d("PRESENTER", t.message?.toString() ?: "No message")
                view?.onToast("Ошибка загрузки персонажей по ids")
            }
        })
    }

    private fun loadLocationByName(name: String){
        val locationId = filters.find { it.second == name }?.first ?: return
        service.getSingleLocation(locationId).enqueue(object: retrofit2.Callback<Location>{
            override fun onResponse(call: Call<Location>, response: Response<Location>) {
                if(response.code() != 200)
                    return
                response.body()?.let {
                    if(it.residents.size > 1)
                        loadCharactersByIds(getIdsFromResidentUrl(it.residents))
                    else if(it.residents.size == 1)
                        loadCharacterById(getIdsFromResidentUrl(it.residents))
                    else
                        view?.onCharactersUpdate(emptyList())
                }
            }

            override fun onFailure(call: Call<Location>, t: Throwable) {
                //Ошибка
                view?.onToast("Ошибка загрузки локации по названию")
            }
        })
    }

    private fun getIdsFromResidentUrl(urls: List<String>): String{
        val ids = urls.map { url ->
            val index = url.lastIndexOf("/")
            if(index + 1 <= url.lastIndex)
                url.substring(index + 1)
            else
                ""
        }
        return ids.toString1()
    }

    private fun getFiltersFromLocations(locations: List<Location>): List<Pair<Int, String>>{
        return locations.map { Pair(it.id, it.name) }
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

    interface CharactersView: IView {
        fun onFiltersLoad(filters: List<String>)
        fun onCharactersUpdate(newCharacters: List<Character>)
        fun onToast(message: String)
    }
}