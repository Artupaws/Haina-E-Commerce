package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemFacilitiesHotelBinding
import haina.ecommerce.model.hotels.FacilitiesItem
import haina.ecommerce.model.hotels.newHotel.CommonFacilityItem


class AdapterListCommonFacilities(val context: Context, private val listCommonFacilities: List<CommonFacilityItem?>?):
    RecyclerView.Adapter<AdapterListCommonFacilities.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFacilitiesHotelBinding.bind(view)
        fun bind(itemHaina: CommonFacilityItem?){
            with(binding){
                val icon = HtmlCompat.fromHtml("${itemHaina?.icon}", HtmlCompat.FROM_HTML_MODE_LEGACY)
                tvFacilities.text = icon
                tvNameFacilities.text = itemHaina?.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListCommonFacilities.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFacilitiesHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListCommonFacilities.Holder, position: Int) {
        val photo: CommonFacilityItem? = listCommonFacilities?.get(position)
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listCommonFacilities?.size!!


}