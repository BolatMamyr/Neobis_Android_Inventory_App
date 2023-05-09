package com.example.inventoryapp.presenter

import android.content.Context
import android.util.Log
import com.example.inventoryapp.db.AppDatabase
import com.example.inventoryapp.model.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ShoesPresenter(private val context: Context) : Contract.Presenter {
    private var view: Contract.ViewContract? = null
    private val shoesDao = AppDatabase.getInstance(context)?.shoesDao()!!

    // needed to get latest search results when Fragment is recreated or resumed because even if
    // EditText text is persisted, TextWatcher gets destroyed which leads to loss of data until
    // text is changed
    private var searchResults = emptyList<Shoes>()
    private var archivedSearchResults = emptyList<Shoes>()

    override fun attachView(view: Contract.ViewContract) {
        this.view = view
    }

    override fun addShoes(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.insertShoes(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun detachView() {
        this.view = null
    }

    override fun getAllShoes() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                when(val vieww = view) {
                    is Contract.ShoesListView -> {
                        shoesDao.getAllShoes().collectLatest { list ->
                            withContext(Dispatchers.Main) {
                                vieww.showShoes(list)
                            }
                        }
                    }
                    is Contract.ArchivedShoesListView -> {
                        shoesDao.getAllArchivedShoes().collectLatest { list ->
                            withContext(Dispatchers.Main) {
                                vieww.showShoes(list)
                            }
                        }
                    }
                    else -> {}
                }
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun getArchivedShoes() {
        CoroutineScope(Dispatchers.IO).launch {
            getArchivedShoes()
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
                shoesDao.archive(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun unarchive(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.unarchive(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun delete(shoes: Shoes) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.deleteShoes(shoes)
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    override fun search(query: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.search(query).collectLatest { list ->
                    searchResults = list
                    withContext(Dispatchers.Main) {
                        when(val vieww = view) {
                            is Contract.ShoesListView -> {
                                vieww.showShoes(list)
                            }
                            is Contract.ArchivedShoesListView -> {
                                vieww.showShoes(list)
                            }
                            else -> {}
                        }
                    }
                }
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    fun getSearchResults() = searchResults

    override fun searchArchived(query: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                shoesDao.searchArchived(query).collectLatest { list ->
                    archivedSearchResults = list
                    withContext(Dispatchers.Main) {
                        when(val vieww = view) {
                            is Contract.ShoesListView -> {
                                vieww.showShoes(list)
                            }
                            is Contract.ArchivedShoesListView -> {
                                vieww.showShoes(list)
                            }
                            else -> {}
                        }
                    }
                }
            }
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown Error")
        }
    }

    fun getArchivedSearchResults() = archivedSearchResults


}