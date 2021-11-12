package com.app.yuyata.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dosisTable")
data class Dosis(
    @ColumnInfo(name = "estado") val dosisEstado:Boolean,
    @ColumnInfo(name = "nombre") val dosisNombre:String,
    @ColumnInfo(name = "fecha") val dosisFecNac: String,
    val pacienteCreatorId: Int,
){
    @PrimaryKey(autoGenerate = true) var dosis_id=0
}