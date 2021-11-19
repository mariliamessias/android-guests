package br.com.guests.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.guests.R
import br.com.guests.service.model.GuestModel
import br.com.guests.view.listener.GuestListener

class GuestViewHolder (itemView: View, private val listener: GuestListener): RecyclerView.ViewHolder(itemView) {

    // atribui os valores ao layout
    fun bind(guest: GuestModel){
        val textName = itemView.findViewById<TextView>(R.id.text_name)
        textName.text = guest.name

        //evento que irá acionar a fragment
        textName.setOnClickListener{
            listener.onClick(guest.id)
        }

        //clicar e segurar
        textName.setOnLongClickListener{

            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover){ dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }
    }

}