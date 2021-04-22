package haina.ecommerce.adapter.flight

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemDestinationCountryBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.flight.DestinationCity
import haina.ecommerce.model.flight.DestinationCountry
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity
import haina.ecommerce.view.flight.fragment.ScheduleFlightFragment


class AdapterFlightDestinationCountry(val context: Context, private val listDestinationCountry: List<DestinationCountry?>?):
    RecyclerView.Adapter<AdapterFlightDestinationCountry.Holder>(){

    private var listCity = listOf(
            DestinationCity("Jakarta","CGK", "Soekarno Hatta","Indonesia"),
            DestinationCity("Yogyakarta","JOG", "Adisutjipto","Indonesia"),
            DestinationCity("Palembang","PLM", "Sultan Mahmud Badaruddin II","Indonesia"),
            DestinationCity("Semarang","SRG", "Achmad Yani","Indonesia"),
            DestinationCity("Pontianak","PNK", "Supadio","Indonesia"),
            DestinationCity("Surabaya","SUB", "Juanda","Indonesia"),
            DestinationCity("Medan","KNO", "Kualanamu","Indonesia")
    )
    private var broadcaster:LocalBroadcastManager?=null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemDestinationCountryBinding.bind(view)
        fun bind(itemHaina: DestinationCountry){
            with(binding){
              binding.tvNameCountry.text = itemHaina.nameCountry
              setupListCity(binding, listCity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFlightDestinationCountry.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDestinationCountryBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterFlightDestinationCountry.Holder, position: Int) {
        val photo: DestinationCountry = listDestinationCountry?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listDestinationCountry?.size!!

    private fun setupListCity(binding:ListItemDestinationCountryBinding,data:List<DestinationCity>){
        binding.rvCountry.apply {
            adapter = AdapterFlightDestinationCity(context, data)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

}