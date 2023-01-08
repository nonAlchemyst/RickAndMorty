package com.example.rickandmorty.presentation.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.rickandmorty.presentation.presenters.base.BasePresenter

open class BaseFragment<T: ViewBinding, E: BasePresenter<R>,  R: BasePresenter.IView>: Fragment(), BasePresenter.IView {

    private lateinit var _binding: T
    private lateinit var bindingInitializer: (LayoutInflater) -> T
    private lateinit var _presenter: E

    val binding
        get() = _binding
    val presenter
        get() = _presenter

    fun setBinding(initializer: (LayoutInflater) -> T){
        bindingInitializer = initializer
    }

    fun setPresenter(presenter: E){
        _presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(this::_presenter.isInitialized == false)
            throw java.lang.Exception("Presenter must not be null")
        if(this::bindingInitializer.isInitialized == false)
            throw java.lang.Exception("Binding initializer must not be null")
        _presenter.attachView(thisToView())
        _binding = bindingInitializer.invoke(inflater)
        return _binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _presenter.detachView()
    }

    fun showBackButton(){
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    fun hideBackButton(){
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    private fun thisToView(): R{
        return (this as? R)?.let { it } ?: throw java.lang.Exception("Type error between fragment and presenter")
    }

//    fun <T> MutableLiveData<T>.onChanged(block: (it: T?) -> Unit) {
//        this.observe(this@BaseFragment as LifecycleOwner) { block(this.value) }
//    }

}