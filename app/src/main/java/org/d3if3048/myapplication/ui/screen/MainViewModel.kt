package org.d3if3048.myapplication.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3048.myapplication.database.TahananDao
import org.d3if3048.myapplication.model.Tahanan

class MainViewModel(dao: TahananDao) : ViewModel() {
    val data: StateFlow<List<Tahanan>> = dao.getTahanan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}