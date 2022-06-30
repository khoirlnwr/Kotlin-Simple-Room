package com.example.kotlinroom.adapters

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinroom.R
import com.example.kotlinroom.UpdateProductActivity
import com.example.kotlinroom.repository.Product

class ProductListAdapter(private val dataSet: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvPrice: TextView

        init {
            tvName = view.findViewById(R.id.tv_item_name)
            tvPrice = view.findViewById(R.id.tv_harga_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewPerItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)

        return ItemViewHolder(viewPerItem)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        Log.e(TAG, "onBindViewHolder: " + dataSet[position].productName)

        holder.tvName.text = dataSet[position].productName
        holder.tvPrice.text = dataSet[position].productPrice.toString()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}