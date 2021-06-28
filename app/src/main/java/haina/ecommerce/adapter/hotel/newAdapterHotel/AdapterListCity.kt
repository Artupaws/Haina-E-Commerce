package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemCityHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataCities


class AdapterListCity(val context: Context, private var listCity: List<DataCities?>?,
                      private val itemAdapterCallback:ItemAdapterCallBack):
    RecyclerView.Adapter<AdapterListCity.Holder>(), Filterable {

    private var listCityResult :List<DataCities?>? = listCity

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCityHotelBinding.bind(view)
        fun bind(hotelHaina: DataCities, itemAdapterCallback:ItemAdapterCallBack){
            with(binding){
              tvNameCity.text = hotelHaina.name
                cvClick.setOnClickListener {
                    hotelHaina.idDarma?.let { it1 -> itemAdapterCallback.onClick(cvClick, it1) }
                }
                Glide.with(context).load(hotelHaina.image).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivHotels)
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

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null){
                    listCityResult
                } else {
                    listCityResult?.filter {
                        it?.name?.toLowerCase()!!.contains(querySearch,ignoreCase = true)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listCity = p1?.values as List<DataCities?>
                notifyDataSetChanged()
            }

        }
    }


}