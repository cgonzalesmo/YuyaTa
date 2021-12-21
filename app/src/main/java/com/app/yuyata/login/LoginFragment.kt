package com.app.yuyata.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.yuyata.dashboard.DashboardActivity
import com.app.yuyata.R
import com.app.yuyata.data.database
import com.app.yuyata.databinding.FragmentLoginBinding
import com.app.yuyata.viewModel.repository

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )


        //Variables para la conexion a los datos de la tabla register
        val application = requireNotNull(this.activity).application
        val dao = database.invoke(application).detailDao()
        val repository = repository(dao)
        val factory = LoginViewModelFactory(repository, application)

        //Conexion con el Layout Fragment Login y su logica
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.myLoginViewModel = loginViewModel

        //En caso se quiera ir a register desde login
        /*loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                //displayUsersList()
                loginViewModel.doneNavigatingRegiter()
            }
        })*/

        //Observar si se ha cambiado el valor de error de null en campos  (errorNullToast==true)
        loginViewModel.errorNullToast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), this.getString(R.string.CompleteFields), Toast.LENGTH_SHORT).show()
                loginViewModel.doneErrorNullToast()
            }
        })

        //Observar si se ha cambiado el valor de error de Usuario  (errotoastUsername==true)
        loginViewModel.errotoastUsername .observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), this.getString(R.string.ThisUserDoesNotExit), Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        /*loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })*/

        //Observar si se ha finalizado el Login  (navigateToHome==true)
        loginViewModel.navigateToHome.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("Login F","Se finalizo login")
                Log.i("Login F: ",loginViewModel.inputId.toString())
                navigateToHome(loginViewModel.inputId)
                loginViewModel.doneNavigatingUserDetails()
            }
        })
        return binding.root
    }




    private fun navigateToHome(userId: Int) {
        Log.i("Login F","Entrar a home")
        //val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        //NavHostFragment.findNavController(this).navigate(action)
        val dashBoardIntent = Intent(getActivity(), DashboardActivity::class.java)
        dashBoardIntent.putExtra("userId",userId)
        getActivity()?.startActivity(dashBoardIntent)
    }

    //En caso se quiera ir a register desde login
    /*private fun displayUsersList() {
        Log.i("MYTAG","insidisplayUsersList")
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }*/


}