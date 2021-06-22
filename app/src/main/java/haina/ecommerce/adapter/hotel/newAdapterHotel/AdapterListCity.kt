package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCityHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataCities


class AdapterListCity(val context: Context, private val listCity: List<DataCities?>?,
                      private val itemAdapterCallback:ItemAdapterCallBack):
    RecyclerView.Adapter<AdapterListCity.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCityHotelBinding.bind(view)
        fun bind(hotelHaina: DataCities, itemAdapterCallback:ItemAdapterCallBack){
            with(binding){
              tvNameCity.text = hotelHaina.name
                cvClick.setOnClickListener {
                    hotelHaina.idDarma?.let { it1 -> itemAdapterCallback.onClick(cvClick, it1) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListCity.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCityHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListCity.Holder, position: Int) {
        val data: DataCities = listCity?.get(position)!!
        holder.bind(data, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listCity?.size!!

    interface ItemAdapterCallBack{
        fun onClick(view: View, idDarma: Int)
    }


}