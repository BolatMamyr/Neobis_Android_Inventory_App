package com.example.inventoryapp.presenter

import android.content.Context
import android.util.Log
import com.example.inventoryapp.db.AppDatabase
import com.example.inventoryapp.model.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoesPresenter(private val context: Context) : Contract.Presenter {
    private var view: Contract.ViewContract? = null
    private val shoesDao = AppDatabase.getInstance(context)?.shoesDao()!!
    private val archivedDao = AppDatabase.getInstance(context)?.archivedShoesDao()!!

    override fun addShoes(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.insertShoes(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun attachView(view: Contract.ViewContract) {
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
                    (view as Contract.ShoesListView).showShoes(shoes)
                }
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun updateShoes(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.updateShoes(shoes)
            }
        } catch (e: Exception) {
            Log.d("MyLog", "ShoesPresenter: updateShoes ERROR")
            view?.showError(e.message ?: "Unknown Error")
        }
    }


    override fun archive(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
//                archivedDao.insert(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }
}