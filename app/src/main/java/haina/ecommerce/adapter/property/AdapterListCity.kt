package haina.ecommerce.adapter.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.databinding.ListItemProvinceBinding
import haina.ecommerce.model.property.DataCity
import haina.ecommerce.model.property.DataFacilitiesProperty
import haina.ecommerce.model.property.DataProvince
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class AdapterListCity(val context: Context, private var dataCity: List<DataCity?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListCity.Holder>(), Filterable {

    private var listResult : List<DataCity?>? = dataCity

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemProvinceBinding.bind(view)
        fun bind(itemHaina: DataCity, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvProvince.text = itemHaina.name
                tvProvince.setOnClickListener {
                    itemAdapterCallback.onClickAdapterCity(tvProvince, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListCity.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProvinceBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListCity.Holder, position: Int) {
        val depart: DataCity = dataCity?.get(position)!!
        holder.bind(depart, itemAdapterCallback)

    }

    interface ItemAdapterCallback{
        fun onClickAdapterCity(view: View, data:DataCity)
    }

    override fun getItemCount(): Int = dataCity!!.size

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null){
                    listResult
                } else {
                    listResult?.filter {
                        it?.name?.toLowerCase()!!.contains(querySearch,ignoreCase = true)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                dataCity = p1?.values as List<DataCity?>?
                notifyDataSetChanged()
            }

        }
    }
}