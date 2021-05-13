package com.example.superlist.listcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.superlist.databinding.ActivityAddListCardBinding
import com.example.superlist.listcards.data.ItemCard
import com.example.superlist.listcards.data.ListCard
import com.example.superlist.MainActivity
import com.example.superlist.auth
import com.example.superlist.ref

class AddListCardActivity: AppCompatActivity(){

    private lateinit var binding: ActivityAddListCardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListCardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.saveListCardBt.setOnClickListener {

            val title = binding.title.text.toString()

            binding.title.setText("")

            addListCard(title)

            val ipm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


            ref.child(auth.uid.toString()).setValue(listCardCollection)

            finish()

        }
    }
    private fun addListCard(title: String) {

        val itemList = mutableListOf<ItemCard>()
        val listcard = ListCard(System.currentTimeMillis().toInt(), title, 0, itemList)

        ListCardDepositoryManager.instance.addListCard(listcard)

    }


}





