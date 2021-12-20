package com.app.yuyata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.yuyata.data.Dosis
import java.text.SimpleDateFormat

class CustomAdapter(private val items: List<Dosis>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var btnDosis: LinearLayout
        var nombre: TextView
        var hora: TextView
        var cantidad: TextView
        var estado: TextView

        init {

            btnDosis = view.findViewById(R.id.btnDosis);
            nombre = view.findViewById(R.id.txtNombreMedicamento)
            hora = view.findViewById(R.id.txtHora)
            cantidad = view.findViewById(R.id.txtCantidad);
            estado = view.findViewById(R.id.txtEstado);
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        //Nombre
        viewHolder.nombre.text = items[position].dosisNombre;

        //Fecha y hora
        val sdf = SimpleDateFormat("hh:mm");
        val horaText = sdf.format(items[position].dosisFecNac);
        viewHolder.hora.text = "Hora: ${horaText}";

        //TODO: NO HAY CANTIDAD

        //Estado
        if(items[position].dosisEstado){
            viewHolder.estado.text = "COMPLETO";
            viewHolder.estado.setBackgroundResource(R.drawable.state_bg_cm);
        }else{
            viewHolder.estado.text = "NO REALIZADO";
            viewHolder.estado.setBackgroundResource(R.drawable.state_bg_nr);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

}