package org.d3if3048.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3048.myapplication.model.Tahanan

@Dao
interface TahananDao {
    @Insert
    suspend fun insert(listTahanan: Tahanan)

    @Update
    suspend fun update(listTahanan: Tahanan)

    @Query("SELECT * FROM listTahanan ORDER BY ruangan")
    fun getTahanan() : Flow<List<Tahanan>>

    @Query("SELECT * FROM listTahanan WHERE id = :id")
    suspend fun getTahananById(id: Long): Tahanan

    @Query("DELETE FROM listTahanan WHERE id = :id")
    suspend fun deleteById(id: Long)
}