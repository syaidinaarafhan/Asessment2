package org.d3if3048.myapplication.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3048.myapplication.database.TahananDao
import org.d3if3048.myapplication.model.Tahanan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: TahananDao) : ViewModel() {

    private  val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    fun insert(
        nama: String, umur: String, tanggal_keluar: String,
        ruangan: String, deskripsi: String
    ){
        val tahanan = Tahanan(
            nama = nama,
            umur = umur,
            tanggal_masuk = formatter.format(Date()),
            tanggal_keluar = tanggal_keluar,
            ruangan = ruangan,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO){
            dao.insert(tahanan)
        }
    }

    suspend fun getTahanan(id: Long): Tahanan? {
        return dao.getTahananById(id)
    }

    fun update(
        id: Long, nama: String, umur: String, tanggal_keluar: String,
        ruangan: String, deskripsi: String
    ){
        val tahanan = Tahanan(
            id = id,
            nama = nama,
            umur = umur,
            tanggal_masuk = formatter.format(Date()),
            tanggal_keluar = tanggal_keluar,
            ruangan = ruangan,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO){
            dao.update(tahanan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}