package com.app.yuyata.viewModel

import androidx.lifecycle.LiveData
import com.app.yuyata.data.Dosis
import com.app.yuyata.data.Paciente
import com.app.yuyata.data.PacienteDosis
import com.app.yuyata.data.dao

class repository (private val modelDao: dao) {

    val allPacientes: LiveData<List<PacienteDosis>> = modelDao.getPacientes()

    fun getDosisById(paciente_id:Int): List<PacienteDosis>{
        return modelDao.getDosisById(paciente_id)
    }

    suspend fun insertPaciente(paciente: List<Paciente>){
        modelDao.insertPaciente(paciente)
    }
    suspend fun updatePaciente(paciente: List<Paciente>){
        modelDao.updatePaciente(paciente)
    }
    suspend fun deletePaciente(paciente: List<Paciente>){
        modelDao.deletePaciente(paciente)
    }
    suspend fun insertDosis(dosis: List<Dosis>){
        modelDao.insertDosis(dosis)
    }
}