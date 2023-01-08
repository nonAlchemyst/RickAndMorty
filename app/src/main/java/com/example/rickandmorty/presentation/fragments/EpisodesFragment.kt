package com.example.rickandmorty.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.episodes.Episode
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.presentation.adapters.EpisodesAdapter
import com.example.rickandmorty.presentation.fragments.base.BaseFragment
import com.example.rickandmorty.presentation.presenters.EpisodesPresenter

class EpisodesFragment: BaseFragment<FragmentEpisodesBinding, EpisodesPresenter, EpisodesPresenter.EpisodesView>(),
    EpisodesPresenter.EpisodesView{

    private val adapter = EpisodesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding { FragmentEpisodesBinding.inflate(layoutInflater) }
        setPresenter(EpisodesPresenter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUI()
        presenter.loadEpisodes()
    }

    private fun setUI() = with(binding) {
        val itemDecoration = DividerItemDecoration(this@EpisodesFragment.context, LinearLayoutManager.VERTICAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.characters_item_divider))
        }
        episodesList.addItemDecoration(itemDecoration)
        episodesList.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(recyclerView.canScrollVertically(1) == false){
                    presenter.onListEnd()
                }
            }
        })
        episodesList.adapter = adapter
    }

    override fun onEpisodesUpdates(newEpisodes: List<Episode>) {
        adapter.update(newEpisodes)
    }

    override fun onToast(message: String) {
        Toast.makeText(this@EpisodesFragment.context, message, Toast.LENGTH_SHORT).show()
    }

}