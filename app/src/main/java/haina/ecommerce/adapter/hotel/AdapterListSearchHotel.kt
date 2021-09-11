package haina.ecommerce.adapter.hotel

import android.content.Context
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemHotelSearchBinding
import haina.ecommerce.model.flight.MealInfosItem
import haina.ecommerce.model.hotels.HotelSearchItem


class AdapterListSearchHotel(val context: Context, private val listSearch: List<HotelSearchItem?>?,private val callback:CallbackInterface):
    RecyclerView.Adapter<AdapterListSearchHotel.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemHotelSearchBinding.bind(view)
        fun bind(itemHaina: HotelSearchItem){
            with(binding){
                binding.tvNameHotel.text=itemHaina.name
                binding.tvAddress.text=itemHaina.city
                binding.tvType.text=itemHaina.type
                cvClick.setOnClickListener{
                    callback.passSearch(itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListSearchHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHotelSearchBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListSearchHotel.Holder, position: Int) {
        val photo: HotelSearchItem = listSearch?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listSearch?.size!!

    interface CallbackInterface {
        fun passSearch(data:HotelSearchItem)
    }

}