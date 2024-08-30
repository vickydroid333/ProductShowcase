package com.vickydroid.productshowcase.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vickydroid.productshowcase.databinding.ActivityProductDetailBinding
import com.vickydroid.productshowcase.model.Product
import com.vickydroid.productshowcase.network.NetworkUtils


class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }

        // Get product from intent
        val product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
        if (product != null) {
            displayProductDetails(product)
        } else {
            Toast.makeText(this, "Product data not available", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayProductDetails(product: Product) {
        // Set product details to views
        binding.productName.text = product.name
        binding.productDescription.text = product.description ?: "No description available"
        binding.productPrice.text = product.price?.let { "$$it" } ?: "Price not available"
        binding.productLanguages.text = product.availableLanguages?.joinToString(", ") ?: "Languages not available"

        // Load product image using Glide
        product.imagePath?.let {
            Glide.with(this)
                .load(it.wide)
                .into(binding.productImage)
        } ?: run {
            // Set a placeholder if image is not available
            binding.productImage.setImageResource(com.vickydroid.productshowcase.R.drawable.ic_launcher_background)
        }
    }

    companion object {
        private const val EXTRA_PRODUCT = "extra_product"

        fun start(context: Context, product: Product) {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            context.startActivity(intent)
        }
    }
}