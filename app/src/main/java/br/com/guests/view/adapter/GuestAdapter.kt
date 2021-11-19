package br.com.guests.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.guests.R
import br.com.guests.service.model.GuestModel
import br.com.guests.view.listener.GuestListener
import br.com.guests.view.viewholder.GuestViewHolder

class GuestAdapter : RecyclerView.Adapter<GuestViewHolder>() {

    private var mGuestList: List<GuestModel> = arrayListOf()
    //referenciamos a interface que criamos
    private lateinit var mListener : GuestListener

    //Cria o suficiente para utilizar, ela é mais performática
    // cria linha uma a uma para poder receber os registros
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        //injetando o layout onde será criado
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_guest, parent, false)
        return GuestViewHolder(item, mListener)
    }

    //Atribui os valores para o componente
    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(mGuestList[position])
    }

    override fun getItemCount(): Int {
        return mGuestList.count()
    }

    fun updateGuests(list: List<GuestModel> ){
        mGuestList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: GuestListener){
        mListener = listener
    }
}