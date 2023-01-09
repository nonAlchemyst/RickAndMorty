package com.example.rickandmorty.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.episodes.Episode
import com.example.rickandmorty.databinding.EpisodesItemBinding

class EpisodesAdapter: RecyclerView.Adapter<EpisodesAdapter.Holder>() {

    private var episodes = emptyList<Episode>()

    class Holder(private val binding: EpisodesItemBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(episode: Episode) = with(binding) {
            val stringUtils = root.context
            this.name.text = "${stringUtils.getString(R.string.name)}: ${episode.name}"
            this.episode.text = "${stringUtils.getString(R.string.dimension)}: ${episode.episode}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = EpisodesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
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