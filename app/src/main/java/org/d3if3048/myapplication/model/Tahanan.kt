package org.d3if3048.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listTahanan")
data class Tahanan (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val umur: String,
    val tanggal_masuk: String,
    val tanggal_keluar: String,
    val ruangan: String,
    val deskripsi: String,
)