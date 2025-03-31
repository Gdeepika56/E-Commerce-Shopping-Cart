package com.example.ecommerceproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val cartDao: CartDao

    companion object{

        private lateinit var instance: AppDatabase
        fun getInstance(context: Context): AppDatabase{

            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "ECart"
                ).allowMainThreadQueries().build()
            }
            return instance
        }

    }


}