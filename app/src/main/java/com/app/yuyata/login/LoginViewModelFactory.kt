package com.app.yuyata.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.yuyata.viewModel.repository
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private  val repository: repository,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}