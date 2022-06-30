package com.example.kotlinroom.repository

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// give the database information, e.g the table, export schema information, etc
@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductRoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    private class ProductDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    populateDatabase(it.productDao())
                }
            }
        }


        suspend fun populateDatabase(productDao: ProductDao) {
            // delete all content
            productDao.deleteAll()

            // Add sample words
            var product = Product(productName = "Tumblr", productPrice = 10.9)
            productDao.insert(product)

            product = Product(productName = "Headset", productPrice = 99.9)
            productDao.insert(product)

            product = Product(productName = "Airpods", productPrice = 99.9)
            productDao.insert(product)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ProductRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ProductRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductRoomDatabase::class.java,
                    "product_database"
                )
                    .addCallback(ProductDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}