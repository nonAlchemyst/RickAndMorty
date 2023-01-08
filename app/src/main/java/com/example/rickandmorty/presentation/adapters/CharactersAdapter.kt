package com.example.rickandmorty.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.databinding.CharactersItemBinding

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.Holder>() {

    private var characters = emptyList<Character>()

    class Holder(private val binding: CharactersItemBinding): RecyclerView.ViewHolder(binding.root){

        //Согласен, хардкодить - себя не уважать
        //Потом доделаю
        fun bind(character: Character) = with(binding) {
            Glide.with(image).load(character.image).into(image)
            name.text = "Name: " + character.name
            status.text = "Status: " + character.status
            origin.text = "Origin: " + character.origin.name
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newCharacters: List<Character>){
        characters = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CharactersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size

}