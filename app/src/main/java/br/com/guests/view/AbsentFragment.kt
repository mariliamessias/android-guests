package br.com.guests.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.guests.R
import br.com.guests.service.constants.GetConstants
import br.com.guests.view.adapter.GuestAdapter
import br.com.guests.view.listener.GuestListener
import br.com.guests.viewModel.GuestsViewModel
import kotlinx.android.synthetic.main.nav_header_main.*

class AbsentFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener : GuestListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_absent, container, false)

        //RecyclerView
        // 1 - Obter a recycler
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_absents)
        // 2 - Definir um layout
        recycler.layoutManager = LinearLayoutManager(context)
        // 3 - Definir um adapter -> pega os dados e junta com o layout
        recycler.adapter = mAdapter

        // objeto anônimo
        mListener = object : GuestListener{
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(GetConstants.GUESTID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GetConstants.FILTER.ABSENT)
            }

        }

        // atrobuimos o evento ao adapter
        mAdapter.attachListener(mListener)
        observer()

        return root
    }

    //para que traga sempre que a activity entrar em foco
    override fun onResume() {
        super.onResume()
        mViewModel.load(GetConstants.FILTER.ABSENT)
    }

    private fun observer() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }
}