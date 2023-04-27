package com.example.inventoryapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.inventoryapp.model.Shoes

@Database(entities = [Shoes::class], version = 1, exportSchema = false)
abstract class ShoesDb : RoomDatabase() {

    abstract fun shoesDao(): ShoesDao

    companion object {
        private var INSTANCE: ShoesDb? = null

        fun getInstance(context: Context): ShoesDb? {
            if (INSTANCE == null) {
                synchronized(ShoesDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = ShoesDb::class.java,
                        "shoes.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}