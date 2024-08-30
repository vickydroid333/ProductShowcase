package com.vickydroid.productshowcase.viewmodel

import com.vickydroid.productshowcase.repository.ProductRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vickydroid.productshowcase.model.Product
import com.vickydroid.productshowcase.room.ProductDatabase
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<Map<String, Product>>()
    val products: LiveData<Map<String, Product>> get() = _products

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct

    init {
        viewModelScope.launch {
            _products.value = productRepository.fetchProducts()
        }
    }

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _selectedProduct.value = productRepository.getProduct(productId)
        }
    }
}

