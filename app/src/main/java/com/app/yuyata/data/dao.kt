package com.app.yuyata.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaciente(st:List<Paciente>)

    @Update
    suspend fun updatePaciente(st:List<Paciente>)

    @Delete
    suspend fun deletePaciente(st:List<Paciente>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDosis(st:List<Dosis>)

    @Query("Select * from pacientesTable")
    fun getDosisByPaciente(): LiveData<List<PacienteDosis>>

    @Transaction
    @Query("SELECT * FROM pacientesTable WHERE paciente_id =:paciente_id")
    fun getById(paciente_id:Int): List<PacienteDosis>

    /*@Update
    suspend fun update(personModel: PersonModel)

    @Delete
    suspend fun delete(personModel: PersonModel)

    @Query("Select * from personasTable")
    fun getAllPersons(): LiveData<List<PersonModel>>*/

}