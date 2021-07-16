package com.miqbalkalevi.todolist.util

import androidx.appcompat.widget.SearchView

inline fun SearchView.OnQueryTextChanged(crossinline listener: (String) -> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            listener(p0.orEmpty())
            return true
        }
    })
}