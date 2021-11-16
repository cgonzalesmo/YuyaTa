package com.app.yuyata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.app.yuyata.data.Dosis
import com.app.yuyata.data.Paciente
import com.app.yuyata.data.PacienteDosis
import com.app.yuyata.data.database
import com.app.yuyata.databinding.ActivityWelcomeBinding
import com.app.yuyata.viewModel.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WelcomeActivity : AppCompatActivity() {

    lateinit var ViewModel:viewModel
    private lateinit var binding: ActivityWelcomeBinding

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        activityResult->
        if(activityResult.resultCode == RESULT_OK){
            val name = activityResult.data?.getStringExtra("NAME").orEmpty()
            val lastName = activityResult.data?.getStringExtra("APELLIDO").orEmpty()
            val dni = activityResult.data?.getStringExtra("DNI").orEmpty()

            val pacienteList:List<Paciente> = arrayListOf(
                Paciente(name.toString(),lastName.toString(),"NULL",dni.toString(),"14/11/2021",pacienteSexo = true)
            )
            ViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(viewModel::class.java)
            ViewModel.insertPaciente(pacienteList)
        }else{
            Toast.makeText(this, "Registro de Usuario cancelado", Toast.LENGTH_SHORT).show()        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wvBtnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            responseLauncher.launch(intent)
        }

        /*val pacienteList: List<Paciente> = arrayListOf(
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

        //ViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(viewModel::class.java)
        ViewModel.insertPaciente(pacienteList)
        ViewModel.insertDosis(dosisList)*/


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
    /*fun onLoginButtonClick(view: View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }*/
}