package com.example.swipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swipe.repository.ProductRepository

class ViewModelFactory(private val productRepository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(productRepository) as T
    }
}
