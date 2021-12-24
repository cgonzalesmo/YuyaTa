package com.app.yuyata.dashboard.dosis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.yuyata.R
import com.app.yuyata.data.Dosis
import com.app.yuyata.databinding.FragmentDosisBinding
import com.app.yuyata.viewModel.viewModel


class DosisFragment : Fragment(), DosisClickInterface, DosisClickDeleteInterface {


    private var _binding: FragmentDosisBinding? = null
    private val binding get() = _binding!!

    lateinit var dosisRV: RecyclerView
    lateinit var name: TextView
    lateinit var apellido: TextView
    lateinit var edad: TextView
    lateinit var cantMed: TextView
    lateinit var ViewModel: viewModel
    lateinit var addFAB: ImageButton
    var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(viewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDosisBinding.inflate(inflater, container, false)
        name=binding.tvName
        apellido=binding.tvApellido
        edad=binding.tvEdad
        cantMed=binding.txtMedicamentosP
        addFAB=binding.btnAdd

        ViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            viewModel::class.java)

        val bundle = arguments
        if (bundle != null) {
            userId = bundle.getInt("userId")
        }
        Log.i("Dosis Id",""+userId)




        Log.i("DosisFragment","Entrar a Dosis")
        //Aqui empezamos
        dosisRV = binding.rvDosis
        dosisRV.layoutManager = LinearLayoutManager(requireContext())

        val dosisRVAdapter = DosisRVAdapter(requireContext(), this, this)
        dosisRV.adapter = dosisRVAdapter


        //viewModel = ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ViewModel::class.java) as viewModel

        ViewModel.getDosisById(userId).observe(requireActivity(), Observer { list ->
            list?.let {
                Log.i("Dosis F Id: ",""+userId)
                Log.i("Dosis F: ",it.toString())

                var paciente = it.get(0).paciente
                var dosis = it.get(0).dosis
                name.setText(paciente.pacienteName)
                apellido.setText(paciente.pacienteApellidoMat)
                edad.setText(paciente.pacienteFecNac)
                cantMed.setText(this.getString(R.string.have)+" " +dosis.size+" "+this.getString(R.string.pending))
                Log.i("Dosis",dosis.toString())
                dosisRVAdapter.updateList(dosis)
            }
            addFAB.setOnClickListener{
                val bundle = Bundle()
                bundle.putInt("userId",userId)
                val addDosisFragment = AddDosisFragment()
                addDosisFragment.arguments = bundle
                makeCurrentFragment(addDosisFragment)
                /*val intent = Intent(this@MainActivity,NewPersonActivity::class.java)
                startActivity(intent)
                this.finish()*/
            }
        })
        return binding.root
    }

    override fun onDosisClick(dosis: Dosis) {

        val bundle = Bundle()
        bundle.putString("dosisType","Edit")
        bundle.putInt("userId",userId)
        bundle.putString("name",dosis.dosisNombre)
        bundle.putString("fecha",dosis.dosisFecNac)
        bundle.putBoolean("estado",dosis.dosisEstado)

        val addDosisFragment = AddDosisFragment()
        addDosisFragment.arguments = bundle
        makeCurrentFragment(addDosisFragment)

    }

    override fun onDeleteIconClick(dosis: Dosis) {

    }
    private fun makeCurrentFragment(fragment : Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, fragment)
            commit()
        }



}