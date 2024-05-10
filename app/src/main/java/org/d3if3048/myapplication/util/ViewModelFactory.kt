package org.d3if3048.myapplication.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3048.myapplication.database.TahananDao
import org.d3if3048.myapplication.ui.screen.DetailViewModel
import org.d3if3048.myapplication.ui.screen.MainViewModel

class ViewModelFactory {
    class ViewModelFactory(
        private val dao: TahananDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchceked_cast", "UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(dao) as T
            } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(dao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}