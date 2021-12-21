package com.app.yuyata.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.app.yuyata.R
import com.app.yuyata.data.database
import com.app.yuyata.databinding.FragmentRegisterBinding
import com.app.yuyata.viewModel.repository


class RegisterFragment : Fragment() {

    //ViewModel para la logica del registro
    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )

        //Variables para la conexion a los datos de la tabla register
        val application = requireNotNull(this.activity).application
        val dao = database.invoke(application).detailDao()
        val repository = repository(dao)
        val factory = RegisterViewModelFactory(repository, application)

        //Conexion con el Layout Fragment Register y su logica
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        binding.myRegisterViewModel = registerViewModel



        //Observar si se ha cambiado el valor de error de campos  (errorToastCampos==true)
        registerViewModel.errorToastCampos.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), this.getString(R.string.CompleteFields), Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })
        //Observar si se ha cambiado el valor de error de Usuario existente  (errortoastUsuario==true)
        registerViewModel.errortoastUsuario.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), this.getString(R.string.ExistingUser), Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })

        //Observar si se ha finalizado el registro  (navigateTo==true)
        registerViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("Register Fragment","Observar")
                toWelcome()
                registerViewModel.doneNavigating()
            }
        })

        return binding.root
    }

    //Ir a welcome
    private fun toWelcome() {
        val action =  RegisterFragmentDirections.actionRegisterFragmentToWelcomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
        Log.i("Register Fragment","mostrar Welcome")
    }
}