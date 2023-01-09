package com.example.rickandmorty.presentation.presenters

import com.example.rickandmorty.data.ServiceBuilder
import com.example.rickandmorty.data.episodes.Episode
import com.example.rickandmorty.data.episodes.Episodes
import com.example.rickandmorty.presentation.presenters.base.BasePresenter
import com.example.rickandmorty.presentation.presenters.common.PageInfo
import retrofit2.Call
import retrofit2.Response

class EpisodesPresenter: BasePresenter<EpisodesPresenter.EpisodesView>() {

    private val service = ServiceBuilder.service
    private var page = PageInfo()
    private var episodes = arrayListOf<Episode>()
    override var view: EpisodesView? = null

    fun onListEnd(){
        loadEpisodes()
    }

    fun loadEpisodes(){
        if(page.canNext == false)
            return
        service.getAllEpisodes(page.num).enqueue(object: retrofit2.Callback<Episodes>{
            override fun onResponse(call: Call<Episodes>, response: Response<Episodes>) {
                if(response.code() != 200)
                    return
                response.body()?.let {
                    episodes.addAll(it.results)
                    if(it.info.next != null){
                        page.next()
                    } else {
                        page.stop()
                    }
                    view?.onEpisodesUpdates(episodes)
                }
            }

            override fun onFailure(call: Call<Episodes>, t: Throwable) {
                view?.onToast("Ошибка при загрузке локаций")
            }
        })
    }

    override fun attachView(newView: EpisodesView) {
        view = newView
    }

    override fun detachView() {
        view = null
    }

    interface EpisodesView: IView{
        fun onEpisodesUpdates(newEpisodes: List<Episode>)
        fun onToast(message: String)
    }

}