package com.app.yuyata.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.app.yuyata.data.Paciente
import com.app.yuyata.viewModel.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: repository, application: Application):
    AndroidViewModel(application), Observable{

    init {
        Log.i("Register VM", "init")
    }
    //Bindable para observar si los campos han variado

    @Bindable
    val inputFirstName = MutableLiveData<String?>()

    @Bindable
    val inputLastName = MutableLiveData<String?>()

    @Bindable
    val inputFecha = MutableLiveData<String?>()

    @Bindable
    val inputDni = MutableLiveData<String?>()

    @Bindable
    val inputSexo = MutableLiveData<Boolean>()

    //Corrutinas para codigo asincrono
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Live data para observar los cambios
    private val _navigateto = MutableLiveData<Boolean>()
    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToastCampos = MutableLiveData<Boolean>()
    val errorToastCampos: LiveData<Boolean>
        get() = _errorToastCampos

    private val _errorToastUsuario = MutableLiveData<Boolean>()
    val errortoastUsuario: LiveData<Boolean>
        get() = _errorToastUsuario

    fun sumbitButton() {
        Log.i("Register VM", "Entrar a boton enviar")
        //En el caso que los campos sean nulos se activara la funcion para mostrar que se debe llenar los campos
        if (inputFirstName.value == null || inputLastName.value == null || inputFecha.value == null || inputDni.value == null ) {
            _errorToastCampos.value = true
        }
        else{
            uiScope.launch{
                //Si el usuario existe no se podra crear
                val usersNames = repository.getPacienteDni(inputDni.value!!)
                Log.i("Register VM", usersNames.toString()+ "---")
                if (usersNames != null) {
                    _errorToastUsuario.value = true
                    Log.i("Register VM", "Usuario no es null y existe")
                } else{

                    Log.i("Register VM", "Usuario en creacion")
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val fecha = inputFecha.value!!
                    val dni = inputDni.value!!
                    //val sexo = inputSexo.value!!
                    Log.i("Register VM", "Enviando info")
                    val paciente= Paciente(firstName,lastName,lastName,dni,fecha,true)
                    insert(paciente)
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputDni.value = null
                    inputFecha.value = null
                    inputSexo.value = true
                    _navigateto.value = true
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
        Log.i("Register VM", "Ya se navego")
    }

    fun donetoast() {
        _errorToastCampos.value = false
        Log.i("Register VM", "Ya se mando el error de campos ")
    }

    fun donetoastUserName() {
        _errorToastCampos.value = false
        Log.i("Register VM", "Ya se mando el error de usuario creado")
    }

    private fun insert(user: Paciente): Job = viewModelScope.launch {
        repository.insertPaciente(user)
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}