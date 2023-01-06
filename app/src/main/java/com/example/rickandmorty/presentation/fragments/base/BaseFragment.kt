package com.example.rickandmorty.presentation.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<T: ViewBinding>: Fragment() {

    private lateinit var _binding: T
    private lateinit var bindingInitializer: (LayoutInflater) -> T

    val binding
        get() = _binding

    fun setBinding(initializer: (LayoutInflater) -> T){
        bindingInitializer = initializer
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(this::bindingInitializer.isInitialized == false)
            throw java.lang.Exception("Binding initializer must not be null")
        _binding = bindingInitializer.invoke(inflater)
        return _binding.root
    }

}