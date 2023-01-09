package com.example.rickandmorty.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.locations.Location
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import com.example.rickandmorty.presentation.adapters.LocationsAdapter
import com.example.rickandmorty.presentation.fragments.base.BaseFragment
import com.example.rickandmorty.presentation.presenters.LocationsPresenter

class LocationsFragment: BaseFragment<FragmentLocationsBinding, LocationsPresenter, LocationsPresenter.LocationsView>(),
    LocationsPresenter.LocationsView {

    private val adapter = LocationsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding { FragmentLocationsBinding.inflate(layoutInflater) }
        setPresenter(LocationsPresenter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUI()
        presenter.loadLocations()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUI() = with(binding) {
        val itemDecoration = DividerItemDecoration(this@LocationsFragment.context, LinearLayoutManager.VERTICAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.characters_item_divider))
        }
        locationsList.addItemDecoration(itemDecoration)
        locationsList.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(recyclerView.canScrollVertically(1) == false){
                    presenter.onListEnd()
                }
            }
        })
        locationsList.adapter = adapter
    }

    override fun onLocationsUpdate(newLocations: List<Location>) {
        adapter.update(newLocations)
    }

    override fun onToast(message: String) {
        Toast.makeText(this@LocationsFragment.context, message, Toast.LENGTH_SHORT).show()
    }

}