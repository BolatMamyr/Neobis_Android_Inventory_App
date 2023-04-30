package com.example.inventoryapp.presenter

import android.content.Context
import com.example.inventoryapp.db.AppDatabase
import com.example.inventoryapp.db.ShoesDao
import com.example.inventoryapp.model.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoesPresenter(private val context: Context) : Contract.Presenter {
    private var view: Contract.ShoesView? = null
    private val shoesDao = AppDatabase.getInstance(context)?.shoesDao()!!

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
                withContext(Dispatchers.Main) {
                    view?.showShoes(shoes)
                }
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }
}