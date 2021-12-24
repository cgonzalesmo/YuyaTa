package com.app.yuyata.dashboard.dosis

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.yuyata.R
import com.app.yuyata.data.Dosis
import com.app.yuyata.databinding.FragmentAddDosisBinding
import com.app.yuyata.viewModel.viewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AddDosisFragment : Fragment(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentAddDosisBinding? = null
    private val binding get() = _binding!!

    lateinit var ViewModel: viewModel
    lateinit var dosisNameEdT: TextInputEditText
    lateinit var dosisFechaEdt: TextInputEditText
    lateinit var dosisEstado: TextInputEditText
    lateinit var addbtn: FloatingActionButton
    lateinit var setDate: Button
    var dav=0
    var month=0
    var year=0
    var hour=0
    var minute=0

    var savedDav=0
    var savedMonth=0
    var savedYear=0
    var savedHour=0
    var savedMinute=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDosisBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        dosisNameEdT = binding.npTitName
        //dosisFechaEdt = binding.npTitFecha
        //Agregar el estado
        addbtn = binding.npFabSave
        setDate = binding.btnSelectDate
        pickDate()
        ViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            viewModel::class.java)




        val bundle = arguments

        var personID = bundle!!.getInt("userId")
        Log.i("Add Dosis Id",""+bundle!!.getInt("userId"))
        val dosisType:String = bundle!!.getString("dosisType","")
        if(dosisType.equals("Edit")){
            val dosisName = bundle!!.getString("name")
            dosisNameEdT.setText(dosisName)
            val dosisFecha = bundle!!.getString("fecha")
            binding.tvDate.setText(dosisFecha)
            val dosisEstado= bundle!!.getBoolean("estado")
        }
        addbtn.setOnClickListener {
            val dosisName = dosisNameEdT.text.toString()
            //val dosisFecha = dosisFechaEdt.text.toString()
            val dosisFecha = binding.tvDate.text.toString()

            //if(dosisType.equals("Edit")){
            //Para editar
            //}
            //else{
            val dosisList: List<Dosis> = arrayListOf(
                Dosis(true,dosisName,dosisFecha,personID)
            )
            ViewModel.insertDosis(dosisList)
            Toast.makeText(context, "Dosis add", Toast.LENGTH_LONG).show()
            //}
            val bundle = Bundle()
            bundle.putInt("userId",personID)

            val dosisFragment = DosisFragment()
            dosisFragment.arguments = bundle
            makeCurrentFragment(dosisFragment)
        }



        return binding.root


    }
    private fun makeCurrentFragment(fragment : Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    private fun getDateTimeCalendar(){
        val cal:Calendar = Calendar.getInstance()
        dav= cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }
    private fun pickDate(){
        setDate.setOnClickListener{
            getDateTimeCalendar()
            DatePickerDialog(requireContext(),this,year,month,dav).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDav = dayOfMonth
        savedMonth = month
        savedYear = year


        TimePickerDialog(requireContext(),this,hour,minute,true).show()

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        binding.tvDate.text = this.getString(R.string.date) +": $savedDav-$savedMonth-$savedYear "+ this.getString(R.string.hour) +": $savedHour:$savedMinute"

    }


}