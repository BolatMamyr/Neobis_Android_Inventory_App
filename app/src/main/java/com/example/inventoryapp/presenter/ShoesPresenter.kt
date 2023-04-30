package com.example.inventoryapp.presenter

import com.example.inventoryapp.db.ShoesDao
import com.example.inventoryapp.model.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoesPresenter(private val shoesDao: ShoesDao) : Contract.Presenter {
    private var view: Contract.ShoesView? = null

    override fun addShoes(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.insertShoes(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun attachView(view: Contract.ShoesView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getAllShoes() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val shoes = shoesDao.getAllShoes()
                view?.showShoes(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }
}