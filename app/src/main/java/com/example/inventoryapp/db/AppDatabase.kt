package com.example.inventoryapp.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventoryapp.model.Shoes

@Database(entities = [Shoes::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shoesDao(): ShoesDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = AppDatabase::class.java,
                        "shoes.db"
                    ).build()
                }
            }
            Log.d("MyLog", "DB inst = $INSTANCE")
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}