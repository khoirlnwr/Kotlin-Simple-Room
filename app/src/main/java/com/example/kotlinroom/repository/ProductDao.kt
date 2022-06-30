package com.example.kotlinroom.repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table ORDER BY name ASC")
    fun getAlphabetizedProduct(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Query("UPDATE product_table SET name = :name, price = :price WHERE id = :id")
    suspend fun update(name: String, price: Double, id: Int)

    @Query("DELETE FROM product_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM product_table WHERE id = :id")
    fun getDetailProduct(id: Int): Flow<Product>

    @Query("SELECT COUNT(*) FROM product_table")
    fun getTotalNumberOfRow(): Flow<Int>
}