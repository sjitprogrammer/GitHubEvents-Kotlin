package com.aodev.githubevents.listener

import android.view.View

interface OnItemClickListener {
    fun onClickedItem(view: View, id: String, position:Int)
}