package com.example.inventoryapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.inventoryapp.model.Shoes

@Dao
interface ArchivedShoesDao {

    @Insert
    fun insert(shoes: Shoes)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<Shoes>

    @Delete
    fun delete(shoes: Shoes)


    companion object {
        const val TABLE_NAME = "archived_shoes_table"
    }
}