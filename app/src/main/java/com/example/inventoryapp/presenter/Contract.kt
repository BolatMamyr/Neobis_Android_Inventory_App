package com.example.inventoryapp.presenter

import com.example.inventoryapp.model.Shoes

interface Contract {

    interface ShoesView {
        fun showShoes(shoes: List<Shoes>)
        fun showError(message: String)
    }

    interface Presenter {
        fun addShoes(shoes: Shoes)
        fun getAllShoes()
        fun updateShoes(shoes: Shoes)
        fun attachView(view: ShoesView)
        fun detachView()
    }
}