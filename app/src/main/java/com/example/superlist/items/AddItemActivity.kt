package com.example.superlist.items

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.superlist.ListCardHolder
import com.example.superlist.auth
import com.example.superlist.databinding.ActivityAddItemBinding
import com.example.superlist.listcards.data.ItemCard
import com.example.superlist.listcards.listCardCollection
import com.example.superlist.listcards.progress_update
import com.example.superlist.ref

class AddItemActivity: AppCompatActivity(){
    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.saveItemBt.setOnClickListener {

            val title = binding.title.text.toString()
            var item_new = ListCardHolder.PickedListCard?.let { it1 ->
                ItemCard(
                    item_id = it1.id,
                    check = false,
                    title = title
                )
            }
            listCardCollection.forEach {
                if (it.id == ListCardHolder.PickedListCard?.id ){
                    if (item_new != null) {
                        it.list.add(item_new)
                        progress_update(it.id)
                    }
                }

            }

            binding.title.setText("")

            val ipm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            ref.child(auth.uid.toString()).setValue(listCardCollection)
            finish()

        }
    }

}

