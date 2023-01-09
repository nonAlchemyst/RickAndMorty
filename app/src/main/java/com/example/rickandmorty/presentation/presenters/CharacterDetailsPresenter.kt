package com.example.rickandmorty.presentation.presenters

import com.example.rickandmorty.data.Global
import com.example.rickandmorty.data.ServiceBuilder
import com.example.rickandmorty.data.episodes.Episode
import com.example.rickandmorty.presentation.presenters.base.BasePresenter
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.presentation.presenters.common.getIdsFromUrls
import retrofit2.Call
import retrofit2.Response

class CharacterDetailsPresenter: BasePresenter<CharacterDetailsPresenter.CharacterDetailsView>() {

    private val service = ServiceBuilder.service
    private var character: Character? = null
    override var view: CharacterDetailsView? = null

    override fun attachView(newView: CharacterDetailsView) {
        view = newView
    }

    override fun detachView() {
        view = null
    }

    fun loadEpisodes(){
        val episodeCount = character?.episode?.size ?: 0
        val ids = getIdsFromUrls(character?.episode ?: emptyList())
        if (episodeCount > 1){
            loadEpisodes(ids)
        } else if (episodeCount == 1){
            loadEpisode(ids)
        } else {
            view?.onEpisodesUpdates(emptyList())
        }
    }

    fun loadCharacter(){
        character = Global.getPickedCharacter() ?: return
        view?.onCharacterUpdate(character ?: return)
    }

    private fun loadEpisodes(ids: String){
        service.getMultipleEpisodes(ids).enqueue(object: retrofit2.Callback<List<Episode>>{
            override fun onResponse(call: Call<List<Episode>>, response: Response<List<Episode>>) {
                if(response.code() != 200){
                    view?.onEpisodesUpdates(emptyList())
                    return
                }
                response.body()?.let {
                    view?.onEpisodesUpdates(it)
                }
            }

            override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                view?.onToast("Ошибка загрузки эпизодов")
            }
        })
    }

    private fun loadEpisode(id: String){
        val id = id.toIntOrNull() ?: let {
            view?.onToast("Ошибка загрузки эпизодов")
            return
        }
        service.getSingleEpisode(id).enqueue(object: retrofit2.Callback<Episode>{
            override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                if(response.code() != 200){
                    view?.onEpisodesUpdates(emptyList())
                    return
                }
                response.body()?.let {
                    view?.onEpisodesUpdates(listOf(it))
                }
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {
                view?.onToast("Ошибка загрузки эпизодов")
            }
        })
    }

    interface CharacterDetailsView: IView{
        fun onEpisodesUpdates(newEpisodes: List<Episode>)
        fun onCharacterUpdate(newCharacter: Character)
        fun onToast(message: String)
    }
}