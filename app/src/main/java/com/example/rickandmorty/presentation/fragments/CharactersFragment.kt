package com.example.rickandmorty.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.ServiceBuilder
import com.example.rickandmorty.data.characters.Characters
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.presentation.adapters.CharactersAdapter
import com.example.rickandmorty.presentation.fragments.base.BaseFragment
import retrofit2.Call
import retrofit2.Response

class CharactersFragment: BaseFragment<FragmentCharactersBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding { FragmentCharactersBinding.inflate(it) }
    }

    //Ильяс, мне совершенно поебать, что сейчас перемешаны слои
    //Я потом это поправлю
    //3 ночи за окном :/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        val adapter = CharactersAdapter()
        charactersList.addItemDecoration(DividerItemDecoration(this@CharactersFragment.context, LinearLayoutManager.VERTICAL))
        charactersList.adapter = adapter
        ServiceBuilder().build().getAllCharacters().enqueue(object: retrofit2.Callback<Characters>{
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if(response.code() != 200)
                    return
                response.body()?.let {
                    adapter.update(it.results)
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                Toast.makeText(this@CharactersFragment.context, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}