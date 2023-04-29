package com.example.inventoryapp.db

import androidx.room.*
import com.example.inventoryapp.model.Shoes

@Dao
interface ShoesDao {

    @Insert
    fun insertShoes(shoes: Shoes)

    @Query("SELECT * FROM shoes_table")
    fun getAllShoes(): List<Shoes>

    @Update
    fun updateShoes(shoes: Shoes)

    @Delete
    fun deleteShoes(shoes: Shoes)
}