package haina.ecommerce.adapter.hotel

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemHotelsBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.hotels.Hotels
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity
import haina.ecommerce.view.hotels.DetailHotelsActivity


class AdapterListHotel(val context: Context, private val listHotel: List<Hotels?>?):
    RecyclerView.Adapter<AdapterListHotel.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemHotelsBinding.bind(view)
        fun bind(itemHaina: Hotels){
            with(binding){
                tvNameHotel.text = itemHaina.nameHotel
                tvLocationHotel.text = itemHaina.location
                val startPrice = "Start From : ${itemHaina.startPrice}"
                tvStartPrice.text = startPrice
                cvClick.setOnClickListener {
                    val intent = Intent(context, DetailHotelsActivity::class.java)
                        .putExtra("nameHotel", itemHaina.nameHotel)
                        .setFlags(FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHotelsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHotel.Holder, position: Int) {
        val photo: Hotels = listHotel?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listHotel?.size!!


}