package com.vickydroid.productshowcase.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vickydroid.productshowcase.adapter.ProductAdapter
import com.vickydroid.productshowcase.databinding.ActivityMainBinding
import com.vickydroid.productshowcase.network.ApiClient
import com.vickydroid.productshowcase.network.NetworkUtils
import com.vickydroid.productshowcase.repository.ProductRepository
import com.vickydroid.productshowcase.room.ProductDatabase
import com.vickydroid.productshowcase.viewmodel.ProductViewModel
import com.vickydroid.productshowcase.viewmodel.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!NetworkUtils.isNetworkAvailable(this)) {
            NetworkUtils.showNoInternetSnackbar(this) {
                // Retry network check
                checkNetworkAndLoadData()
            }
        } else {
            checkNetworkAndLoadData()
        }
    }

    private fun checkNetworkAndLoadData() {
        binding.progressBar.visibility = View.VISIBLE
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Initialize ViewModel
            val productDao = ProductDatabase.getDatabase(this).productDao()
            val productService = ApiClient.productService
            val repository = ProductRepository(productDao, productService)
            viewModel = ViewModelProvider(this, ProductViewModelFactory(repository))[ProductViewModel::class.java]

            // Initialize Adapter
            productAdapter = ProductAdapter(emptyList()) { product ->
                // Handle item click
                Toast.makeText(this, "Clicked: ${product.name}", Toast.LENGTH_SHORT).show()
            }

            // Setup RecyclerView
            binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewProducts.adapter = productAdapter

            // Observe products LiveData
            viewModel.products.observe(this) { products ->
                binding.progressBar.visibility = View.GONE
                products?.let {
                    productAdapter.updateProducts(it.values.toList())
                }
            }
        } else {
            binding.progressBar.visibility = View.GONE
            NetworkUtils.showNoInternetSnackbar(this) {
                // Retry network check
                checkNetworkAndLoadData()
            }
        }
    }
}

