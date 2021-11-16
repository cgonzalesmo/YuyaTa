package com.app.yuyata.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Paciente::class, Dosis::class],
    version = 4,
    exportSchema = false
)
abstract class database : RoomDatabase() {
    abstract fun detailDao(): dao

    companion object{

        @Volatile private var INSTANCE: database? =null
        private val lock= Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(lock){
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                database::class.java,
                "Recetas"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}