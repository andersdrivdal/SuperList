package com.example.superlist.listcards

import com.example.superlist.listcards.data.ListCard


var listCardCollection = mutableListOf<ListCard>()
class ListCardDepositoryManager {

    var onListCards: ((List<ListCard>) -> Unit)? = null

    fun addListCard(listcard: ListCard) {
        listCardCollection.add(listcard)
        onListCards?.invoke(listCardCollection)
    }

    companion object {
        val instance = ListCardDepositoryManager()
    }

}
