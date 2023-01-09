package com.example.rickandmorty.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmorty.R
import com.example.rickandmorty.data.episodes.Episode
import com.example.rickandmorty.databinding.EpisodesHorizontalItemBinding
import com.example.rickandmorty.databinding.EpisodesItemBinding
import com.example.rickandmorty.presentation.adapters.common.Orientation

class EpisodesAdapter(private val orientation: Orientation = Orientation.VERTICAL): RecyclerView.Adapter<EpisodesAdapter.Holder>() {

    private var episodes = emptyList<Episode>()

    abstract class Holder(view: View): ViewHolder(view){
        abstract fun bind(episode: Episode)
    }

    class HolderVertical(private val binding: EpisodesItemBinding): Holder(binding.root){

        @SuppressLint("SetTextI18n")
        override fun bind(episode: Episode) = with(binding) {
            val stringUtils = root.context
            this.name.text = "${stringUtils.getString(R.string.name)}: ${episode.name}"
            this.episode.text = "${stringUtils.getString(R.string.dimension)}: ${episode.episode}"
        }

    }

    class HolderHorizontal(private val binding: EpisodesHorizontalItemBinding): Holder(binding.root){

        override fun bind(episode: Episode) = with(binding) {
            this.episode.text = episode.episode
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = when(orientation){
            Orientation.HORIZONTAL -> {
                HolderHorizontal(EpisodesHorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            Orientation.VERTICAL -> {
                HolderVertical(EpisodesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(newEpisodes: List<Episode>){
        episodes = newEpisodes
        notifyDataSetChanged()
    }

}