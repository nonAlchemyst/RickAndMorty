package com.example.rickandmorty.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.Character
import com.example.rickandmorty.databinding.CharactersItemBinding

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.Holder>() {

    private var characters = emptyList<Character>()
    private var onPickListener: ((Character) -> Unit)? = null

    class Holder(private val binding: CharactersItemBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(character: Character, onPickListener: ((Character) -> Unit)?) = with(binding) {
            val stringUtils = root.context
            Glide.with(image).load(character.image).into(image)
            name.text = "${stringUtils.getString(R.string.name)}: ${character.name}"
            status.text = "${stringUtils.getString(R.string.status)}: ${character.status}"
            origin.text = "${stringUtils.getString(R.string.origin)}: ${character.origin.name}"
            root.setOnClickListener { onPickListener?.invoke(character) }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newCharacters: List<Character>){
        characters = newCharacters
        notifyDataSetChanged()
    }

    fun addOnPickCharacter(onPick: (Character) -> Unit){
        onPickListener = onPick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CharactersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(characters[position], onPickListener)
    }

    override fun getItemCount() = characters.size

}