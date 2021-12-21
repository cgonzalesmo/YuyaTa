package com.app.yuyata.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.yuyata.CustomAdapter
import com.app.yuyata.R
import com.app.yuyata.dashboard.dosis.DosisFragment
import com.app.yuyata.data.Dosis
import com.app.yuyata.data.database
import com.app.yuyata.databinding.ActivityDashboardBinding
import com.app.yuyata.graphics.GraphicsFragment
import com.app.yuyata.maps.MapsFragment
import com.app.yuyata.viewModel.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DashboardActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dosisFragment = DosisFragment()

        val mapsFragment = MapsFragment()

        val graphsFragment = GraphicsFragment()

        makeCurrentFragment(dosisFragment)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_dosis -> makeCurrentFragment(dosisFragment)
                R.id.ic_mapa -> makeCurrentFragment(mapsFragment)
                R.id.ic_graph -> makeCurrentFragment(graphsFragment)
            }
            true
        }



        /*val dao = database.invoke(application).detailDao()
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
        recyclerView.adapter = adapter;*/



    }

    private fun makeCurrentFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, fragment)
            commit()
    }
}