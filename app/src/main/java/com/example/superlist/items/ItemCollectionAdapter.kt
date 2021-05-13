package com.example.superlist.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.databinding.ItemLayoutBinding
import com.example.superlist.listcards.data.ItemCard

class ItemCollectionAdapter(private var itemcards:List<ItemCard>,
                            private val onItemRemoveClicked:(ItemCard)-> Unit,
                            private var onCheckboxChecked: (Int)-> Unit):
                            RecyclerView.Adapter<ItemCollectionAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, itemcard: ItemCard, onItemRemoveClicked: (ItemCard) -> Unit, onCheckboxChecked: (Int) -> Unit) {
            binding.title.text = itemcard.title
            binding.checkBox.isChecked = itemcard.check
            binding.removeItem.setOnClickListener{
                onItemRemoveClicked(itemcard)
            }
            binding.checkBox.setOnClickListener{
                onCheckboxChecked(position)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemCollectionAdapter.ViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemcard = itemcards[position]
        holder.bind(position, itemcard, onItemRemoveClicked, onCheckboxChecked)


    }

    override fun getItemCount(): Int = itemcards.size

    public fun updateCollection(newItemCards:List<ItemCard>) {
        itemcards = newItemCards
        notifyDataSetChanged()
    }


}