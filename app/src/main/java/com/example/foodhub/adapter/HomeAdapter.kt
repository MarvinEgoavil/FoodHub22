package com.example.foodhub.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.RowMenuBinding
import com.example.foodhub.models.Product
import java.util.function.Predicate
import java.util.stream.Collectors

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listTemp: MutableList<Product> = ArrayList()
    private var listOriginal: MutableList<Product> = ArrayList()

    companion object {
        lateinit var mListener: OnItemClickListener
        lateinit var mContext: Context

    }


    constructor(context: Context) : this() {
        mContext = context
    }

    constructor(context: Context, dataList: List<Product>) : this() {
        mContext = context
        listTemp = dataList as MutableList<Product>
        listOriginal.addAll(listTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowMenuBinding = RowMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTemp.get(position))
    }

    override fun getItemCount(): Int {
        if (listTemp != null && listTemp.size > 0)
            return listTemp.size
        else
            return 0
    }

    fun setListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun filter(newText: String) {
        if (newText.length <= 0) {
            listTemp.clear()
            listTemp.addAll(listOriginal)
        } else {
            listTemp.clear()
            val collect: MutableList<Product> = listOriginal.stream()
                .filter(Predicate<Product> { i: Product ->
                    i.name.toString().toLowerCase().contains(newText)
                })
                .collect(Collectors.toList<Any>()) as MutableList<Product>
            listTemp.addAll(collect)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: RowMenuBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var product: Product

        fun bind(item: Product) {
            binding.containerMain.setOnClickListener(this)
            product = item
            val placeholder: Drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher)
            Glide.with(mContext)
                .load(product.img_url)
                .placeholder(placeholder)
                .error(placeholder)
                .centerCrop()
                .into(binding.image)

            binding.txtNameRestaurant.setText(product.name)
            binding.txtProductDescription.setText(product.tags)
            binding.txtRating.setText("" + product.ratings)

            when (product.ratings) {
                0 -> {
                    binding.txtRatingTag.setText("Muy Malo")
                }
                1 -> {
                    binding.txtRatingTag.setText("Malo")
                }
                2 -> {
                    binding.txtRatingTag.setText("Bueno")
                }
                3 -> {
                    binding.txtRatingTag.setText("Muy Bueno")
                }
                4 -> {
                    binding.txtRatingTag.setText("Recomendable")
                }
                5 -> {
                    binding.txtRatingTag.setText("Excelente")
                }
            }
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.container_main -> {
                    if (mListener != null) {
                        mListener.onItemClick(view, product, adapterPosition, false)
                    }
                }
            }
        }

    }

    interface OnItemClickListener {

        fun onItemClick(view: View, product: Product, position: Int, longPress: Boolean): Boolean
    }
}