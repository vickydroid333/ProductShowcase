package com.vickydroid.productshowcase.network

import com.vickydroid.productshowcase.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("test/products.php")
    suspend fun getProducts(): Response<ProductResponse>
}