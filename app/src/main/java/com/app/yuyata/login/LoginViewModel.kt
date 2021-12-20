package com.app.yuyata.login

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.yuyata.viewModel.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: repository, application: Application) :
    AndroidViewModel(application), Observable {


    var inputId: Int = 0

    @Bindable
    val inputDni = MutableLiveData<String?>()

    @Bindable
    //val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Livedata para observar los cambios

    // Livedata navigate para ir a Registrar
    /*private val _navigatetoRegister = MutableLiveData<Boolean>()
    val navigatetoRegister: LiveData<Boolean>
        get() = _navigatetoRegister*/

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _errorNullToast = MutableLiveData<Boolean>()
    val errorNullToast: LiveData<Boolean>
        get() = _errorNullToast

    private val _errorToastUsername = MutableLiveData<Boolean>()
    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    /*private val _errorToastInvalidPassword = MutableLiveData<Boolean>()
    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword*/

    // Funcion registrar
    /*fun signUP() {
        _navigatetoRegister.value = true
    }*/

    fun loginButton() {
        //Si existiera contraseña:
        //if (inputUsername.value == null || inputPassword.value == null) {
        //Como no existe contraseña solo se verifica DNI en caso sea null se manda errorNullToast
        if (inputDni.value == null) {
            _errorNullToast.value = true
        }
        //Se verifica que no estan nulll los campos
        else {
            uiScope.launch {
                //Se verifica que exista el usuario
                val user = repository.getPacienteDni(inputDni.value!!)
                if (user != null) {
                    inputId = user.paciente.paciente_id

                    //if(usersNames.passwrd == inputPassword.value){
                    inputDni.value = null
                    //inputPassword.value = null
                    _navigateToHome.value = true
                    /*}else{
                        _errorToastInvalidPassword.value = true
                    }*/
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }

    /*fun doneNavigatingRegiter() {
        _navigatetoRegister.value = false
    }*/

    fun doneNavigatingUserDetails() {
        _navigateToHome.value = false
    }


    fun doneErrorNullToast() {
        _errorNullToast.value = false
        Log.i("Login VM", "Error nulo ya ejecutado ")
    }


    fun donetoastErrorUsername() {
        _errorToastUsername.value = false
        Log.i("Login VM", "Error usuario no existe ya ejecutado  ")
    }

    /*fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
        Log.i("MYTAG", "Done taoasting ")
    }*/


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}
