package com.example.swipe.api

import com.example.swipe.model.AddProductResponse
import com.example.swipe.model.ProductList
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("get")
    suspend fun getProducts(): Response<ProductList>

    @Multipart
    @POST("add")
    fun addProduct(
        @Part("product_name") product_name: String,
        @Part("product_type") product_type: String,
        @Part("price") price: Float,
        @Part("tax") tax: Float,
        @Part files: MultipartBody.Part
    ): Call<AddProductResponse>
}
