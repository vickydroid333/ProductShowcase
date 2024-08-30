package com.vickydroid.productshowcase.repository

import com.vickydroid.productshowcase.model.Product
import com.vickydroid.productshowcase.network.ProductService
import com.vickydroid.productshowcase.room.ProductDao

class ProductRepository(private val productDao: ProductDao, private val productService: ProductService) {

    // Fetch products from the network and save them to the local database
    suspend fun fetchProducts(): Map<String, Product>? {
        val response = productService.getProducts()
        response.let {
            it.body()?.products?.values?.let { it1 -> productDao.insertProducts(it1.toList()) }
        }
        return response.body()?.products
    }

    // Get a specific product from local database
    suspend fun getProduct(productId: String): Product? {
        return productDao.getProduct(productId)
    }
}
