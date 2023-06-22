package com.example.swipe.repository

import androidx.lifecycle.MutableLiveData
import com.example.swipe.api.ApiInterface
import com.example.swipe.model.ProductList

class ProductRepository(private val apiInterface: ApiInterface) {

    private val productsLiveData = MutableLiveData<ProductList>()

    val products: MutableLiveData<ProductList>
        get() = productsLiveData

    suspend fun getProduct() {
        val result = apiInterface.getProducts()
        if (result.body() != null) {
            productsLiveData.postValue(result.body())
        }
    }
}