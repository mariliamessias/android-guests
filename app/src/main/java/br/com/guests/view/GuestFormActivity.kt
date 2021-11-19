package br.com.guests.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.guests.viewModel.GuestFormViewModel
import br.com.guests.R
import br.com.guests.service.constants.GetConstants
import kotlinx.android.synthetic.main.activity_guest_form.*

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        observe()
        loadData()

        //ao carregar a tela para um novo convidado será default
        radio_presence.isChecked = true
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle!= null){
            mGuestId = bundle.getInt(GetConstants.GUESTID)
            // carregar o usuário
            mViewModel.load(mGuestId)
        }
    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, Observer {
            if(it){
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {
            edit_name.setText(it.name)
            if(it.presence) {
                radio_presence.isChecked = true
            }else{
                radio_absense.isChecked = true
            }

        })
    }

    override fun onClick(view: View) {
        val id = view.id
        if(id == R.id.button_save){
            val name = edit_name.text.toString()
            val presence = radio_presence.isChecked

            mViewModel.save(mGuestId, name, presence)
        }
    }

    private fun setListeners() {
        button_save.setOnClickListener(this)
    }

}