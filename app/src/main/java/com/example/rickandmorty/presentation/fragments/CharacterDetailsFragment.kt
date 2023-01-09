package com.example.rickandmorty.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.data.episodes.Episode
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmorty.presentation.adapters.EpisodesAdapter
import com.example.rickandmorty.presentation.adapters.common.Orientation
import com.example.rickandmorty.presentation.fragments.base.BaseFragment
import com.example.rickandmorty.presentation.fragments.common.toGone
import com.example.rickandmorty.presentation.fragments.common.toVisible
import com.example.rickandmorty.presentation.presenters.CharacterDetailsPresenter

class CharacterDetailsFragment: BaseFragment<FragmentCharacterDetailsBinding, CharacterDetailsPresenter, CharacterDetailsPresenter.CharacterDetailsView>(),
    CharacterDetailsPresenter.CharacterDetailsView{

    private val adapter = EpisodesAdapter(Orientation.HORIZONTAL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding { FragmentCharacterDetailsBinding.inflate(layoutInflater) }
        setPresenter(CharacterDetailsPresenter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUI()
        presenter.loadCharacter()
        presenter.loadEpisodes()
    }

    override fun onEpisodesUpdates(newEpisodes: List<Episode>) {
        binding.apply {
            if (newEpisodes.isEmpty()){
                episodesTitle.toGone()
                episodesList.toGone()
            } else {
                episodesTitle.toVisible()
                episodesList.toVisible()
            }
        }
        adapter.update(newEpisodes)
    }

    override fun onCharacterUpdate(newCharacter: Character) {
        setCharacterData(newCharacter)
    }

    override fun onToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUI() = with(binding) {
        val itemDecoration = DividerItemDecoration(this@CharacterDetailsFragment.context, LinearLayoutManager.HORIZONTAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.divider_16_horizontal))
        }
        episodesList.addItemDecoration(itemDecoration)
        episodesList.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setCharacterData(character: Character) = with(binding) {
        name.text = "${getString(R.string.name)}: ${character.name}"
        species.text = "${getString(R.string.species)}: ${character.species}"
        gender.text = "${getString(R.string.gender)}: ${character.gender}"
        origin.text = "${getString(R.string.origin)}: ${character.origin.name}"
        location.text = "${getString(R.string.location)}: ${character.location.name}"
        Glide.with(image).load(character.image).into(image)
    }

}