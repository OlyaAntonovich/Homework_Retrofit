package com.example.homework_retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework_retrofit.databinding.ItemBinding

class Adapter(
    private val listItems: MutableList<Item>,
    context: Context,
    private val onItemClicked: (Item) -> Unit
) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ItemViewHolder(
        private val binding: ItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            binding.imageAvatar.load(item.avatar)
            binding.textName.text = item.author

            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = ItemBinding.inflate(layoutInflater, parent, false)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listItems.size

}