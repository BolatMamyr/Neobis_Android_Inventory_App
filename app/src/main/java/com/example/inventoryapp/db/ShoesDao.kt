package com.example.inventoryapp.db

import androidx.room.*
import com.example.inventoryapp.model.Shoes

@Dao
interface ShoesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoes(shoes: Shoes)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllShoes(): List<Shoes>

    @Update
    fun updateShoes(shoes: Shoes)

    @Delete
    fun deleteShoes(shoes: Shoes)

    companion object {
        const val TABLE_NAME = "shoes_table"
    }
}