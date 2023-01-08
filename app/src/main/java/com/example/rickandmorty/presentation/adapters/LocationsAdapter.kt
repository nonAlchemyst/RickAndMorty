package com.example.rickandmorty.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.locations.Location
import com.example.rickandmorty.databinding.LocationsItemBinding

class LocationsAdapter: RecyclerView.Adapter<LocationsAdapter.Holder>() {

    private var locations = emptyList<Location>()

    class Holder(private val binding: LocationsItemBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(location: Location) = with(binding) {
            val stringUtils = root.context
            name.text = "${stringUtils.getString(R.string.name)}: ${location.name}"
            dimension.text = "${stringUtils.getString(R.string.dimension)}: ${location.dimension}"
            type.text = "${stringUtils.getString(R.string.type)}: ${location.type}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LocationsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount() = locations.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(newLocations: List<Location>){
        locations = newLocations
        notifyDataSetChanged()
    }

}