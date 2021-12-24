package com.app.yuyata.graphics

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.yuyata.R
import com.app.yuyata.databinding.FragmentGraphicsBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.ArrayList


class GraphicsFragment : Fragment() {

    private var _binding: FragmentGraphicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGraphicsBinding.inflate(inflater, container, false)

        val barChart = binding.barChart

        val medicines = ArrayList<BarEntry>()
        medicines.add(BarEntry(17f,2f))
        medicines.add(BarEntry(18f, 3f))
        medicines.add(BarEntry(19f, 5f))
        medicines.add(BarEntry(20f, 2f))
        medicines.add(BarEntry(21f, 4f))
        medicines.add(BarEntry(22f, 5f))
        medicines.add(BarEntry(23f, 3f))
        medicines.add(BarEntry(24f, 4f))

        val barDataSet = BarDataSet(medicines,this.getString(R.string.Amount_of_medicines_per_day))
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS, 250)
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f


        val barData = BarData(barDataSet)

        barChart.setFitBars(true)
        barChart.data = barData
        barChart.description.isEnabled = false
        barChart.animateY(2000)

        // Inflate the layout for this fragment
        return binding.root
    }
}