package com.example.inventoryapp.db

import androidx.room.*
import com.example.inventoryapp.model.Shoes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ShoesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoes(shoes: Shoes)

    @Query("SELECT * FROM $TABLE_NAME WHERE archived = 0")
    fun getAllShoes(): Flow<List<Shoes>>

    @Query("SELECT * FROM $TABLE_NAME WHERE archived = 1")
    fun getAllArchivedShoes(): Flow<List<Shoes>>

    @Update
    fun updateShoes(shoes: Shoes)

    @Delete
    fun deleteShoes(shoes: Shoes)

    @Update
    fun archive(shoes: Shoes)

    @Update
    fun unarchive(shoes: Shoes)

    @Query("SELECT * FROM $TABLE_NAME WHERE archived = 0 AND (name LIKE '%' || :query || '%' OR brand LIKE '%' || :query || '%')")
    fun search(query: String): Flow<List<Shoes>>

    @Query("SELECT * FROM $TABLE_NAME WHERE archived = 1 AND (name LIKE '%' || :query || '%' OR brand LIKE '%' || :query || '%')")
    fun searchArchived(query: String): Flow<List<Shoes>>


    companion object {
        const val TABLE_NAME = "shoes_table"
    }
}