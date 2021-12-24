package com.app.yuyata.dashboard.dosis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.yuyata.R
import com.app.yuyata.data.Dosis
import com.app.yuyata.databinding.FragmentAddDosisBinding
import com.app.yuyata.viewModel.viewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class AddDosisFragment : Fragment() {

    private var _binding: FragmentAddDosisBinding? = null
    private val binding get() = _binding!!

    lateinit var ViewModel: viewModel
    lateinit var dosisNameEdT: TextInputEditText
    lateinit var dosisFechaEdt: TextInputEditText
    lateinit var dosisEstado: TextInputEditText
    lateinit var addbtn: FloatingActionButton

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
        dosisFechaEdt = binding.npTitFecha
        //Agregar el estado
        addbtn = binding.npFabSave
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
            dosisFechaEdt.setText(dosisFecha)
            val dosisEstado= bundle!!.getBoolean("estado")
        }
        addbtn.setOnClickListener {
            val dosisName = dosisNameEdT.text.toString()
            val dosisFecha = dosisFechaEdt.text.toString()
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


}