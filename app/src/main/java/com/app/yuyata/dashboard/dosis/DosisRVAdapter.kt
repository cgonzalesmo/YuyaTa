package com.app.yuyata.dashboard.dosis

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.yuyata.R
import com.app.yuyata.data.Dosis

class DosisRVAdapter(
    val context: Context,
    val dosisClickInterface: DosisClickInterface,
    val dosisClickDeleteInterface: DosisClickDeleteInterface
): RecyclerView.Adapter<DosisRVAdapter.ViewHolder>(){

    private val allDosis = ArrayList<Dosis>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTV = itemView.findViewById<TextView>(R.id.txtNombreMedicamento)
        val horaTV = itemView.findViewById<TextView>(R.id.txtHora)
        val cantTV = itemView.findViewById<TextView>(R.id.txtCantidad)
        val estTV = itemView.findViewById<TextView>(R.id.txtEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("RV: ","OnCreate VH")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("RV: ","OnBind VH")
        holder.nameTV.setText(allDosis[position].dosisNombre)
        //holder.horaTV.setText(allPersons.get(position).paciente.pacienteName)

        //Fecha y hora
        //val sdf = SimpleDateFormat("hh:mm");
        //val horaText = sdf.format(allDosis.get(position).dosisFecNac);
        //holder.horaTV.setText("Hora: ${horaText}")
        holder.horaTV.setText(allDosis[position].dosisFecNac)
        //TODO: NO HAY CANTIDAD

        //Estado
        if(allDosis[position].dosisEstado){
            holder.estTV.text = "COMPLETO";
            holder.estTV.setBackgroundResource(R.drawable.state_bg_cm);
        }else{
            holder.estTV.text = "NO REALIZADO";
            holder.estTV.setBackgroundResource(R.drawable.state_bg_nr);
        }
        holder.itemView.setOnClickListener{
            dosisClickInterface.onDosisClick(allDosis.get(position))
        }

    }

    override fun getItemCount(): Int {
        Log.i("RV: ","getCount "+allDosis.size)
        return allDosis.size
    }
    fun updateList(newList: List<Dosis>){
        allDosis.clear()
        Log.i("RV: ","llego updateList")
        allDosis.addAll(newList)
        notifyDataSetChanged()
    }
}

interface DosisClickInterface{
    fun onDosisClick(dosis: Dosis)
}
interface DosisClickDeleteInterface{
    fun onDeleteIconClick(dosis: Dosis)
}