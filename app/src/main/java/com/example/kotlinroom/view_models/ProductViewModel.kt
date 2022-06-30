package com.example.kotlinroom.view_models

import androidx.lifecycle.*
import com.example.kotlinroom.repository.Product
import com.example.kotlinroom.repository.ProductRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProductViewModel(private val repo: ProductRepository) : ViewModel() {
    val allProducts: LiveData<List<Product>> = repo.products.asLiveData()

    val countProducts: LiveData<Int> = repo.countProducts.asLiveData()

    // suspend = for one shot call
    // flow = for stream result

    // since repo.insert is a suspend function, it has to be called using viewModelScope
    // viewModelScope means that we can call a function through kotlin coroutine
    fun insert(product: Product) = viewModelScope.launch {
        repo.insert(product)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repo.deleteById(id)
    }

    fun update(name: String, price: Double, id: Int) = viewModelScope.launch {
        repo.update(name, price, id)
    }

}

class ProductViewModelFactory(private val repo: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}