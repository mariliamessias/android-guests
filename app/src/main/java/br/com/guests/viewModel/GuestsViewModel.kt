package br.com.guests.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.guests.service.constants.GetConstants
import br.com.guests.service.model.GuestModel
import br.com.guests.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter:Int){
        if(filter == GetConstants.FILTER.EMPTY){
            mGuestList.value = mGuestRepository.getAll()
        }else if( filter == GetConstants.FILTER.ABSENT){
            mGuestList.value = mGuestRepository.getAbsent()
        }else {
            mGuestList.value = mGuestRepository.getPresent()
        }
    }

    fun delete(id:Int){
        mGuestRepository.delete(id)
    }
}