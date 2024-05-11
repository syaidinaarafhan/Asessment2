package org.d3if3048.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3048.myapplication.model.Tahanan


@Database(entities = [Tahanan::class], version = 1, exportSchema = false)
abstract class dbTahanan : RoomDatabase() {

    abstract val dao: TahananDao

    companion object {

        @Volatile
        private var INSTANCE: dbTahanan? = null

        fun getInstance(context: Context): dbTahanan {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        dbTahanan::class.java,
                        "tahanan.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}