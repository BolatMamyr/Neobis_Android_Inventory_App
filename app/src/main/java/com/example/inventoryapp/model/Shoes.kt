package com.example.inventoryapp.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.*
import com.example.inventoryapp.db.Converters
import com.example.inventoryapp.db.ShoesDao
import kotlinx.parcelize.Parcelize

@Entity(tableName = ShoesDao.TABLE_NAME)
@TypeConverters(Converters::class)
@Parcelize
data class Shoes(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val brand: String,
    val price: Double,
    val quantity: Int,
    val image: Bitmap? = null
): Parcelable
