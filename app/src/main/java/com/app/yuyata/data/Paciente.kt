package com.app.yuyata.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacientesTable")
data class Paciente(
    @ColumnInfo(name = "nombre") val pacienteName:String,
    @ColumnInfo(name = "apellidoPaterno") val pacienteApellidoPat:String,
    @ColumnInfo(name = "apellidoMaterno") val pacienteApellidoMat:String,
    @ColumnInfo(name = "dni") val pacienteDni:String,
    @ColumnInfo(name = "fecNac") val pacienteFecNac: String,
    @ColumnInfo(name = "sexo") val pacienteSexo: Boolean,
    ){
    @PrimaryKey(autoGenerate = true) var paciente_id=0
}