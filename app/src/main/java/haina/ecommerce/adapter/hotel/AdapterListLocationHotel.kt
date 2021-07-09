package haina.ecommerce.adapter.hotel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemLocationHotelsBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.hotels.LocationHotels
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterListLocationHotel(val context: Context, private val listLocationHotel: MutableList<LocationHotels>?,
                               private val itemAdapterCallBack: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterListLocationHotel.Holder>() {

    private var index:Int = 0
    private var responseId:Int =0

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemLocationHotelsBinding.bind(view)
        fun bind(itemHaina: LocationHotels, itemAdapterCallBack: ItemAdapterCallback){
            with(binding) {
                tvNameLocation.text = itemHaina.name
                itemView.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    responseId = if (index==0){ 0
                    } else {
                        itemHaina.idCity
                    }
                    itemAdapterCallBack.onClick(itemHaina)
                }
                if (index == adapterPosition){
                    linearLocation.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                } else {
                    linearLocation.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListLocationHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemLocationHotelsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListLocationHotel.Holder, position: Int) {
        val photo: LocationHotels = listLocationHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallBack)
    }

    override fun getItemCount(): Int = listLocationHotel?.size!!

    interface ItemAdapterCallback{
        fun onClick(data:LocationHotels)
    }


}