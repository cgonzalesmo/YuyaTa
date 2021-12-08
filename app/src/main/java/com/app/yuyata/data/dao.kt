package com.app.yuyata.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaciente(st:Paciente)

    @Update
    suspend fun updatePaciente(st:List<Paciente>)

    @Delete
    suspend fun deletePaciente(st:List<Paciente>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDosis(st:List<Dosis>)

    @Query("Select * from pacientesTable")
    fun getPacientes(): LiveData<List<PacienteDosis>>

    @Transaction
    @Query("SELECT * FROM pacientesTable WHERE paciente_id =:paciente_id")
    fun getDosisById(paciente_id:Int): List<PacienteDosis>

    @Transaction
    @Query("SELECT * FROM pacientesTable WHERE dni =:pacienteDni")
    suspend fun getPacienteDni(pacienteDni: String): PacienteDosis

    /*@Update
    suspend fun update(personModel: PersonModel)

    @Delete
    suspend fun delete(personModel: PersonModel)

    @Query("Select * from personasTable")
    fun getAllPersons(): LiveData<List<PersonModel>>*/

}