package com.example.inventoryapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.inventoryapp.db.ArchivedShoesDao
import kotlinx.parcelize.Parcelize

@Entity(tableName = ArchivedShoesDao.TABLE_NAME)
@Parcelize
class ArchivedShoes(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var name: String,
    var brand: String,
    var price: Double,
    var quantity: Int,
    var image: String? = null,
    var archived: Boolean = false
): Parcelable