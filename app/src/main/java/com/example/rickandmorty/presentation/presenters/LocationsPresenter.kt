package com.example.rickandmorty.presentation.presenters

import com.example.rickandmorty.data.ServiceBuilder
import com.example.rickandmorty.data.locations.Location
import com.example.rickandmorty.data.locations.Locations
import com.example.rickandmorty.presentation.presenters.base.BasePresenter
import com.example.rickandmorty.presentation.presenters.common.PageInfo
import retrofit2.Call
import retrofit2.Response

class LocationsPresenter: BasePresenter<LocationsPresenter.LocationsView>() {

    private val service = ServiceBuilder.service
    private var page = PageInfo()
    private var locations = arrayListOf<Location>()
    override var view: LocationsView? = null

    fun onListEnd(){
        loadLocations()
    }

    fun loadLocations(){
        if(page.canNext == false)
            return
        service.getAllLocations(page.num).enqueue(object: retrofit2.Callback<Locations>{
            override fun onResponse(call: Call<Locations>, response: Response<Locations>) {
                if(response.code() != 200)
                    return
                response.body()?.let {
                    locations.addAll(it.results)
                    if(it.info.next != null){
                        page.next()
                    } else {
                        page.stop()
                    }
                    view?.onLocationsUpdate(locations)
                }
            }

            override fun onFailure(call: Call<Locations>, t: Throwable) {
                view?.onToast("Ошибка при загрузке локаций")
            }
        })
    }

    override fun attachView(newView: LocationsView) {
        view = newView
    }

    override fun detachView() {
        view = null
    }

    interface LocationsView: IView {
        fun onLocationsUpdate(newLocations: List<Location>)
        fun onToast(message: String)
    }

}