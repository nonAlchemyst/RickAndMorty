package com.example.rickandmorty.presentation.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ResourceCursorAdapter
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.presentation.adapters.CharactersAdapter
import com.example.rickandmorty.presentation.fragments.base.BaseFragment
import com.example.rickandmorty.presentation.presenters.CharactersPresenter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class CharactersFragment: BaseFragment<FragmentCharactersBinding, CharactersPresenter, CharactersPresenter.CharactersView>(),
    CharactersPresenter.CharactersView {

    private val adapter = CharactersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding { FragmentCharactersBinding.inflate(it) }
        setPresenter(CharactersPresenter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUI()
        presenter.loadFilters()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUI() = with(binding) {
        hideBackButton()
        val itemDecoration = DividerItemDecoration(this@CharactersFragment.context, LinearLayoutManager.VERTICAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.characters_item_divider))
        }
        charactersList.addItemDecoration(itemDecoration)
        charactersList.adapter = adapter
        charactersList.addOnScrollListener(object: OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(recyclerView.canScrollVertically(1) == false){
                    presenter.onListEnd()
                }
            }
        })
        locationsTab.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    presenter.onFilterCharacters(it.text?.toString() ?: return@let)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setTabs(filters: List<String>) = with(binding) {
        filters.forEach {
            locationsTab.addTab(locationsTab.newTab().setText(it))
        }
    }

    override fun onFiltersLoad(filters: List<String>) {
        setTabs(filters)
    }

    override fun onCharactersUpdate(newCharacters: List<Character>) {
        adapter.update(newCharacters)
    }

    override fun onToast(message: String) {
        Toast.makeText(this@CharactersFragment.context, message, Toast.LENGTH_SHORT).show()
    }

}