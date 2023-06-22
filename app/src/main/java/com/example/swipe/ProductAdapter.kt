package com.example.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swipe.model.ProductList

class ProductAdapter(var context: Context, var productData: ProductList) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.findViewById(R.id.productImage)
        var name: TextView = view.findViewById(R.id.productName)
        var type: TextView = view.findViewById(R.id.productType)
        var price: TextView = view.findViewById(R.id.productPrize)
        var tax: TextView = view.findViewById(R.id.productTax)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!productData.get(position).image.isEmpty())
            Glide.with(context).load(productData[position].image).into(holder.img)
        holder.name.text = productData.get(position).product_name
        holder.type.text = productData.get(position).product_type
        holder.price.text = productData.get(position).price.toString()
        holder.tax.text = productData[position].tax.toString()
    }

    override fun getItemCount(): Int {
        return productData.count()
    }
}