package com.example.inventoryapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.ItemRecyclerviewBinding
import com.example.inventoryapp.model.Shoes

class RvAdapter : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    private var list = emptyList<Shoes>()
    var onItemClick: ((Shoes?) -> Unit)? = null
    var onClickShowMore: ((Shoes?) -> Unit)? = null

    inner class MyViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            val item = list[position]
            txtName.text = item.name
            txtBrand.text = item.brand
            val price = String.format("$ %.2f", item.price)
            txtPrice.text = price
            val quantity =
                "${item.quantity} ${holder.itemView.resources.getString(R.string.pieces)}"
            txtQuantity.text = quantity

            Glide.with(holder.itemView.context)
                .load(item.image)
                .placeholder(holder.itemView.context.getDrawable(R.drawable.placeholder))
                .into(img)
        }
        Log.d("MyLog", "RvAdapter: list size = ${list.size}")
        holder.itemView.setOnClickListener { onItemClick?.invoke(list[position]) }

        list[position].let { shoes ->
            holder.binding.btnMore.setOnClickListener {
                onClickShowMore?.invoke(shoes)
            }
        }

    }

    override fun getItemCount(): Int = list.size

    fun getListSize() = list.size

    fun updateData(newList: List<Shoes>) {
        val oldList = list
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(oldList, newList))
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private class MyDiffCallback(
        var oldList: List<Shoes>,
        var newList: List<Shoes>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}