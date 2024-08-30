package com.vickydroid.productshowcase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vickydroid.productshowcase.R
import com.vickydroid.productshowcase.activity.ProductDetailsActivity
import com.vickydroid.productshowcase.databinding.ItemProductBinding
import com.vickydroid.productshowcase.model.Product

class ProductAdapter(
    private var products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.textViewProductName.text = product.name
            binding.textViewProductPrice.text = "$" + product.price.toString()

            // Load image using an image loading library, e.g., Glide
            product.imagePath?.let {
                Glide.with(binding.imageViewProduct.context)
                    .load(it.square) // Assuming 'square' is the image URL you want to load
                    .into(binding.imageViewProduct)
            } ?: run {
                // Set a placeholder if image is not available
                binding.imageViewProduct.setImageResource(R.drawable.ic_launcher_background)
            }

            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)

        // Navigate to ProductDetailsActivity when an item is clicked
        holder.itemView.setOnClickListener {
            ProductDetailsActivity.start(holder.itemView.context, product)
        }
    }

    override fun getItemCount(): Int = products.size
}
