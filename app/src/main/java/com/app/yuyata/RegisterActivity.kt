package com.app.yuyata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.app.yuyata.databinding.ActivityRegisterBinding
import com.app.yuyata.databinding.ActivityWelcomeBinding

class RegisterActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBtnRegister.setOnClickListener {
            val intent = Intent()
            val namUs=binding.rvInputNames.text.toString()
            val apPat=binding.rvInputLastnames.text.toString()
            //val fecNac=binding.rvInputBirthday
            //sexo
            val dniUs = binding.rvInputDni.text.toString()

            intent.putExtra("NAME",namUs)
            intent.putExtra("APELLIDO", apPat)
            //intent.putExtra("FECHA", apPat)
            //sexo
            intent.putExtra("DNI",dniUs)
            setResult(RESULT_OK,intent)
            finish()
        }
    }


    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }
}