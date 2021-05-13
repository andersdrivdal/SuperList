package com.example.superlist.listcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.EXTRA_LISTCARD_INFO
import com.example.superlist.ListCardHolder
import com.example.superlist.auth
import com.example.superlist.databinding.ActivityListCardDetailsBinding
import com.example.superlist.items.AddItemActivity
import com.example.superlist.items.ItemCollectionAdapter
import com.example.superlist.listcards.data.ItemCard
import com.example.superlist.listcards.data.ListCard
import com.example.superlist.ref


class ListCardDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListCardDetailsBinding
    private lateinit var listcard: ListCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, AddItemActivity::class.java)

        binding.AddItemButton.setOnClickListener { startActivity(intent) }

        val receivedListCard = ListCardHolder.PickedListCard

        if(receivedListCard != null){
            listcard= receivedListCard
            Log.i("Details view", receivedListCard.toString())
        } else{

            setResult(RESULT_CANCELED, Intent(EXTRA_LISTCARD_INFO).apply {
            })

            finish()
        }

        UpdateScreen()

    }

    override fun onStart() {
        UpdateScreen()
        super.onStart()
    }

    private fun onRemoveItemClicked(itemcard: ItemCard): Unit{
        listCardCollection.forEach {
            if (it.id == ListCardHolder.PickedListCard?.id){
                it.list.remove(itemcard)
                progress_update(itemcard.item_id)
            }
        }
        ref.child(auth.uid.toString()).setValue(listCardCollection)
        UpdateScreen()
    }

        private fun onCheckboxChecked(position:Int):Unit{
        listCardCollection.forEach{
            if(it.id == ListCardHolder.PickedListCard?.id){
                it.list[position].check = !it.list[position].check
            }
        }
            progress_update(ListCardHolder.PickedListCard!!.id)
            ref.child(auth.uid.toString()).setValue(listCardCollection)
            UpdateScreen()
    }

    private fun UpdateScreen(){
        val itemcard = mutableListOf<ItemCard>()

        listCardCollection.forEach{
            if (it.id == ListCardHolder.PickedListCard?.id){
                it.list.forEach{ x ->
                    if (x.item_id == ListCardHolder.PickedListCard?.id){
                        itemcard.add(x)
                    }
                }
                binding.detailsTitle.text = it.title
                binding.progressBar.progress = it.progress
                binding.ItemsListing.layoutManager = LinearLayoutManager(this)
                binding.ItemsListing.adapter = ItemCollectionAdapter(itemcard,this:: onRemoveItemClicked, this::onCheckboxChecked)

            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

fun progress_update(int: Int){
    listCardCollection.forEach {
        if(it.id == int) {
            var total = it.list.count()
            var checked = 0
            it.list.forEach { itemCard ->
                if(itemCard.check){
                    checked++
                }
            }
            if(total != 0){
                val prosent: Int = checked * 100 / total
                it.progress = prosent
                total = 0
            }else{
                it.progress = 0
            }
        }
    }

}
