package com.example.superlist.listcards.data

import android.os.Parcelable
//import com.example.superlist.items.data.Item
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ListCard(
    var id: Int = System.currentTimeMillis().toInt(),
    var title: String,
    var progress: Int = 0,
    var list: @RawValue MutableList<ItemCard>
):Parcelable

data class ItemCard(var item_id: Int = 0, var check: Boolean = false, var title: String )
