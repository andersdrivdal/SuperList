package com.example.superlist.listcards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.listcards.data.ListCard
import com.example.superlist.databinding.ListCardLayoutBinding


class ListCardCollectionAdapter(private var listcards:List<ListCard>,
                                private val onListCardClicked:(ListCard) -> Unit,
                                private val onRemoveButtonClicked:(ListCard) -> Unit):
                                            RecyclerView.Adapter<ListCardCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listcard: ListCard, onListCardClicked: (ListCard) -> Unit, onRemoveButtonClicked: (ListCard) -> Unit) {
            binding.title.text = listcard.title
            binding.ListProgressBar.progress = listcard.progress
            binding.card.setOnClickListener {
                onListCardClicked(listcard)
            }
            binding.removeListCardButton.setOnClickListener{
                onRemoveButtonClicked(listcard)
            }
        }
    }


    override fun getItemCount(): Int = listcards.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listcard = listcards[position]
        holder.bind(listcard, onListCardClicked, onRemoveButtonClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    public fun updateCollection(newListCards:List<ListCard>){
        listcards = newListCards
        notifyDataSetChanged()
    }

}