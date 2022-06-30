package com.example.kotlinroom.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    // read
    val products: Flow<List<Product>> = productDao.getAlphabetizedProduct()

    // get total number of products
    val countProducts: Flow<Int> = productDao.getTotalNumberOfRow()

    // create
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(name: String, price: Double, id: Int) {
        productDao.update(name = name, price = price, id = id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteById(id: Int) {
        productDao.deleteById(id)
    }

    // get detail product
    fun getDetailProduct(id: Int): Flow<Product> {
        return productDao.getDetailProduct(id)
    }

}