package com.example.rickandmorty.presentation.presenters.base

import com.example.rickandmorty.presentation.presenters.base.BasePresenter.IView

abstract class BasePresenter<T: IView> {

    protected abstract var view: T?

    abstract fun attachView(newView: T)

    abstract fun detachView()

    interface IView{}
}