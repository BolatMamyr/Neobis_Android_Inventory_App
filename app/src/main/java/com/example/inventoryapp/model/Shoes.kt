package com.example.inventoryapp.model

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.room.*
import com.example.inventoryapp.db.Converters
import com.example.inventoryapp.db.ShoesDao
import kotlinx.parcelize.Parcelize

@Entity(tableName = ShoesDao.TABLE_NAME)
@Parcelize
data class Shoes(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var name: String,
    var brand: String,
    var price: Double,
    var quantity: Int,
    var image: String? = null
): Parcelable
