package com.vickydroid.productshowcase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vickydroid.productshowcase.model.Product
import com.vickydroid.productshowcase.network.Converters

@Database(entities = [Product::class], version = 4)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile private var instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                )
                    .fallbackToDestructiveMigration() // Use destructive migration
                    .build().also { instance = it }
            }
    }
}

