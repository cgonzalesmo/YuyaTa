package com.app.yuyata.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.yuyata.data.Dosis
import com.app.yuyata.data.Paciente
import com.app.yuyata.data.PacienteDosis
import com.app.yuyata.data.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class viewModel (application: Application): AndroidViewModel(application){


    val allPacientes: LiveData<List<PacienteDosis>>
    val modelRepository: repository
    init {
        val dao = database.invoke(application).detailDao()
        modelRepository = repository(dao)
        allPacientes = modelRepository.allPacientes
    }

    fun deletePaciente(pacientes:List<Paciente>) = viewModelScope.launch(Dispatchers.IO){
        modelRepository.deletePaciente(pacientes)
    }
    fun updatePaciente(pacientes:List<Paciente>) = viewModelScope.launch(Dispatchers.IO){
        modelRepository.updatePaciente(pacientes)
    }
    fun insertPaciente(pacientes:Paciente) = viewModelScope.launch(Dispatchers.IO){
        modelRepository.insertPaciente(pacientes)
    }
    fun insertDosis(dosis:List<Dosis>) = viewModelScope.launch(Dispatchers.IO){
        modelRepository.insertDosis(dosis)
    }
}