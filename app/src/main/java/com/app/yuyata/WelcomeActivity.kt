package com.app.yuyata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.app.yuyata.data.Dosis
import com.app.yuyata.data.Paciente
import com.app.yuyata.data.PacienteDosis
import com.app.yuyata.data.database
import com.app.yuyata.viewModel.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WelcomeActivity : AppCompatActivity() {

    lateinit var ViewModel:viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val pacienteList: List<Paciente> = arrayListOf(
            Paciente("Juan","Gonza","Moli", "73966540", "null",true),
            Paciente("Carlos","Gonza","Moli", "76494244", "null",true),
            Paciente("Jose","Gonza","Moli", "8354540", "null",true),
            Paciente("Julio","Gonza","Moli", "79546540", "null",true)
        )

        val dosisList: List<Dosis> = arrayListOf(
            Dosis(true,"Paracetamol","null",1),
            Dosis(true,"Ibuprofeno","null",1),
            Dosis(true,"Doloflan","null",1)
        )

        ViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(viewModel::class.java)
        ViewModel.insertPaciente(pacienteList)
        ViewModel.insertDosis(dosisList)


        /*val db = database(this)

        CoroutineScope(Dispatchers.Default).launch {
            db.detailDao().insertPaciente(pacienteList)
            db.detailDao().insertDosis(dosisList)

            val data = db.detailDao().getDosisByPaciente()
            val st = db.detailDao().getById(1)
            Log.i("TAG",st.toString())
        }*/


    }

    //Register button on click
    fun onRegisterButtonClick(view: View){
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    //Login button on click
    fun onLoginButtonClick(view: View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}