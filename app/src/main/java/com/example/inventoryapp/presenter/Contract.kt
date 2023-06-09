package com.example.inventoryapp.presenter

import com.example.inventoryapp.model.Shoes

interface Contract {

    interface ViewContract {
        fun showError(message: String)
    }

    interface ShoesListView : ViewContract {
        fun showShoes(shoes: List<Shoes>)
    }

    interface ArchivedShoesListView : ViewContract {
        fun showShoes(shoes: List<Shoes>)
    }

    interface ShoesView : ViewContract

    interface Presenter {
        fun addShoes(shoes: Shoes)
        fun getAllShoes()
        fun updateShoes(shoes: Shoes)
        fun attachView(view: ViewContract)
        fun detachView()
        fun archive(shoes: Shoes)
        fun getArchivedShoes()
        fun unarchive(shoes: Shoes)
        fun delete(shoes: Shoes)
        fun search(query: String)
        fun searchArchived(query: String)
    }
}