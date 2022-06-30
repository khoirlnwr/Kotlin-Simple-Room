package com.example.kotlinroom.repository

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductApplication : Application() {

    private val appScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ProductRoomDatabase.getDatabase(this, appScope) }
    val repository by lazy { ProductRepository(database.productDao()) }
}