package com.app.yuyata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.app.yuyata.data.Dosis
import com.app.yuyata.data.database
import com.app.yuyata.viewModel.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        val dao = database.invoke(application).detailDao()
        val repository = repository(dao)

        var pacienteId = intent.getIntExtra("userId",0)

        val dosisList: List<Dosis> = arrayListOf(
            Dosis(true,"Paracetamol","12/12/2021 04:00",pacienteId),
            Dosis(true,"Ibuprofeno","12/12/2021 05:00",pacienteId),
            Dosis(true,"Doloflan","12/12/2021 05:00",pacienteId)
        )
        val db = database(this)

        CoroutineScope(Dispatchers.Default).launch {
            db.detailDao().insertDosis(dosisList)
        }

        adapter = CustomAdapter(dosisList);

        var recyclerView = findViewById<RecyclerView>(R.id.rvDosis);
        recyclerView.adapter = adapter;

    }
}