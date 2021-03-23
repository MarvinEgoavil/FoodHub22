 /*package com.example.foodhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.models.ItemList
import java.util.*
import java.util.stream.Collectors

class Adapter( val ItemList: Array<ItemList>) :
    RecyclerView.Adapter<Adapter.>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

    }

    private var mInflater: LayoutInflater? = null
    private var items: MutableList<ItemList>? = null
    private var originalItems: MutableList<ItemList>? = null

    fun Adapter(context: Context?, items: MutableList<ItemList>?) {
        mInflater = LayoutInflater.from(context)
        this.items = items
        originalItems = ArrayList<ItemList>()
        originalItems!!.addAll(items!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater!!.inflate(R.layout.cart_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList: ItemList = items!![position]
        holder.run {
            imageView.setImageResource(itemList.imgResource)
            tvTitulo.text = itemList.titulo
            tvDescription.text = itemList.descripcion
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    // Para el filtrado de palabras
    fun filter(strSearch: String) {
        if (strSearch.isEmpty()) {
            items!!.clear()

            items!!.addAll(originalItems!!)

        } else {
            //Usando expresiones lambda
            items!!.clear()
            val collect: List<ItemList> = originalItems!!.stream()
                .filter { i: ItemList ->
                    i.titulo.toLowerCase().contains(strSearch)
                }
                .collect(Collectors.toList<Any>()) as List<ItemList>
            items!!.addAll(collect)


        }

    }
}  */