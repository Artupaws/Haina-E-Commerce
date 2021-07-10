package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemFacilitiesBinding
import haina.ecommerce.databinding.ListItemFacilitiesHotelBinding
import haina.ecommerce.databinding.ListItemFacilitiesRoomBinding
import haina.ecommerce.model.hotels.FacilitiesItem
import haina.ecommerce.model.hotels.newHotel.FacilityItem


class AdapterFacilitiesHotelMain(val context: Context, private val listFacilities: List<FacilityItem?>?):
    RecyclerView.Adapter<AdapterFacilitiesHotelMain.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFacilitiesBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(itemHaina: FacilityItem?){
            with(binding){
                val icon = HtmlCompat.fromHtml("${itemHaina?.icon}", HtmlCompat.FROM_HTML_MODE_LEGACY)
                faIcon.text = icon
                tvMainFacilities.text = itemHaina?.facilityGroupName
                setupListFacilities(binding, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFacilitiesHotelMain.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFacilitiesBinding.inflate(inflater)
        return Holder(binding.root)
    }

    private fun setupListFacilities(binding: ListItemFacilitiesBinding, position: Int){
        binding.rvChildFacilities.apply {
            adapter = AdapterFacilitiesHotelChild(context, listFacilities?.get(position)?.facilities)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onBindViewHolder(holder: AdapterFacilitiesHotelMain.Holder, position: Int) {
        val photo: FacilityItem? = listFacilities?.get(position)
        holder.bind(photo)
    }
    override fun getItemCount(): Int = listFacilities?.size!!

}