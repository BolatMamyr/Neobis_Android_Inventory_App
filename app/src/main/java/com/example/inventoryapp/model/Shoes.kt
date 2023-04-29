package com.example.inventoryapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoes_table")
data class Shoes(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val brand: String,
    val price: Double,
    val quantity: Int,
    val imgId: Int
)
