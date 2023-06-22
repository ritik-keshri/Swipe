package com.example.swipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.model.ProductList
import com.example.swipe.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(private val productRepository: ProductRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProduct()
        }
    }

    val products: LiveData<ProductList>
        get() {
            return productRepository.products
        }
}