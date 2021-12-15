package com.app.yuyata

import CustomAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.app.yuyata.data.Dosis

class DashboardActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var items = arrayListOf<Dosis>(
            Dosis(true, "Panadol Forte", "12/12/2021 04:00", 1),
            Dosis(false, "Sal de Andrews", "12/12/2021 05:00", 1),
        );

        adapter = CustomAdapter(items);

        var recyclerView = findViewById<RecyclerView>(R.id.rvDosis);
        recyclerView.adapter = adapter;

    }
}