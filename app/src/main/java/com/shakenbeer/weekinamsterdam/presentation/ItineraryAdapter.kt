package com.shakenbeer.weekinamsterdam.presentation

import com.shakenbeer.weekinamsterdam.R
import com.shakenbeer.weekinamsterdam.ui.BaseBindingAdapter

class ItineraryAdapter: BaseBindingAdapter() {

    override fun getObjForPosition(position: Int) = items[position]

    override fun getLayoutIdForPosition(position: Int) = R.layout.item_flight
}