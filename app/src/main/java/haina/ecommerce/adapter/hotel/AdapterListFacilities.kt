package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemFacilitiesHotelBinding
import haina.ecommerce.model.hotels.FacilitiesItem


class AdapterListFacilities(val context: Context, private val listFacilities: List<FacilitiesItem?>?):
    RecyclerView.Adapter<AdapterListFacilities.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFacilitiesHotelBinding.bind(view)
        fun bind(itemHaina: FacilitiesItem){
            with(binding){
                when(itemHaina.facilitiesName){
                    "Television" ->{
                        tvFacilities.text = context.getString(R.string.television)
                    }
                    "Free Wi-Fi" -> {
                        tvFacilities.text = context.getString(R.string.wifi)
                    }
                    "Parking" -> {
                        tvFacilities.text = context.getString(R.string.parking)
                    }
                    "Breakfast" -> {
                        tvFacilities.text = context.getString(R.string.breakfast)
                    }
                    "Bathtub" -> {
                        tvFacilities.text = context.getString(R.string.bathtub)
                    }
                    "Swimming Pool" -> {
                        tvFacilities.text = context.getString(R.string.swimmingpool)
                    }
                    "Spa" -> {
                        tvFacilities.text = context.getString(R.string.spa)
                    }
                    "Fitness Center" -> {
                        tvFacilities.text = context.getString(R.string.dumbbell)
                    }
                }
                tvNameFacilities.text = itemHaina.facilitiesName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListFacilities.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFacilitiesHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListFacilities.Holder, position: Int) {
        val photo: FacilitiesItem = listFacilities?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listFacilities?.size!!


}