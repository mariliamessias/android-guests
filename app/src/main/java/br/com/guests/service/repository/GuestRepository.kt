package br.com.guests.service.repository

import android.content.Context
import br.com.guests.service.model.GuestModel

class GuestRepository (context: Context){

    //Acesso ao banco de dados
    private val mDatabase = GuestDatabase.getDatabase(context).guestDAO()

    fun getAll() : List<GuestModel>{
        return mDatabase.getInvited()
    }

    fun getPresent() : List<GuestModel>{
        return mDatabase.getPresent()
    }

    fun getAbsent() : List<GuestModel>{
        return mDatabase.getAbsent()
    }

    fun get(id: Int) : GuestModel {
        return mDatabase.get(id)
    }

    //CRUD

    fun save(guest: GuestModel) : Boolean{
        return mDatabase.save(guest) > 0
    }

    fun update(guest: GuestModel) : Boolean{
        return mDatabase.update(guest) > 0

    }

    fun delete(guest: GuestModel)  {
        return mDatabase.delete(guest)
    }
}