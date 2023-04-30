package com.example.inventoryapp.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.inventoryapp.db.ShoesDao
import kotlinx.parcelize.Parcelize

@Entity(tableName = ShoesDao.TABLE_NAME)
@Parcelize
data class Shoes(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val brand: String,
    val price: Double,
    val quantity: Int,
    @ColumnInfo(name = "image") val imgUri: String? = null
): Parcelable
