package com.example.superlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.databinding.ActivityMainBinding
import com.example.superlist.listcards.*
import com.example.superlist.listcards.data.ItemCard
import com.example.superlist.listcards.data.ListCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


const val EXTRA_LISTCARD_INFO: String ="com.example.superlist.listcard.info"
const val REQUEST_LISTCARD_DETAILS:Int = 564567

class ListCardHolder {

    companion object {
        var PickedListCard: ListCard? = null
    }

}
val ref = FirebaseDatabase.getInstance("https://superlist-257b1-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Lists")

lateinit var auth: FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val TAG: String = "SuperList:MainActivity"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        signInAnonomoustly()

        val intent = Intent(this, AddListCardActivity::class.java)

        binding.AddListCardButton.setOnClickListener { startActivity(intent) }

        syncronize()

        UpdateMainScreen()

        ListCardDepositoryManager.instance.onListCards = {
            (binding.ListCardListing.adapter as ListCardCollectionAdapter).updateCollection(it)
        }

    }

    fun syncronize() {
        val storedData = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                listCardCollection.clear()
                p0.children.forEach {
                    val id = it.child("id").value.toString().toInt()
                    val list = it.child("list")
                    val progress = it.child("progress").value.toString().toInt()
                    val title = it.child("title").value.toString()
                    val itemList = mutableListOf<ItemCard>()
                    if (list.children.count() != 0) {
                        list.children.forEach { l ->
                            val item_title = l.child("title").value.toString()
                            val item_id = l.child("item_id").value.toString().toInt()
                            val check = l.child("check").value.toString().toBoolean()
                            val itemcard = ItemCard(item_id, check, item_title)
                            itemList.add(itemcard)
                        }

                    }
                    val ListCard = ListCard(id, title, progress, itemList)
                    listCardCollection.add(ListCard)
                }
                UpdateMainScreen()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        ref.child(auth.uid.toString()).addValueEventListener(storedData)
        ref.child(auth.uid.toString()).addListenerForSingleValueEvent(storedData)
    }

    private fun onListCardClicked(listcard: ListCard): Unit {
        ListCardHolder.PickedListCard = listcard

        val intent = Intent(this, ListCardDetailsActivity::class.java)

        startActivity(intent)
    }

    private fun signInAnonomoustly() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login success ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Log in failed", it)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_LISTCARD_DETAILS) {

        }
    }

   private fun onRemoveButtonClicked(listcard: ListCard): Unit{
       listCardCollection.remove(listcard)
       ref.child(auth.uid.toString()).setValue(listCardCollection)
       UpdateMainScreen()
    }

    override fun onStart() {
        UpdateMainScreen()
        super.onStart()
    }

    private fun UpdateMainScreen(){
        binding.ListCardListing.layoutManager = LinearLayoutManager(this)
        binding.ListCardListing.adapter =
            ListCardCollectionAdapter(listCardCollection, this::onListCardClicked, this::onRemoveButtonClicked)
    }


}
