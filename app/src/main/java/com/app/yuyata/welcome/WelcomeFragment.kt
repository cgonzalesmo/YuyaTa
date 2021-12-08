package com.app.yuyata.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.app.yuyata.R



class WelcomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        val registerButton : Button = view.findViewById(R.id.wv_btn_register)
        val loginButton : Button = view.findViewById(R.id.wv_btn_login)

        registerButton.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
        loginButton.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }



        return view
    }


}