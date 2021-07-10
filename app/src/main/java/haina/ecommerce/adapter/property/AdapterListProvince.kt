package haina.ecommerce.adapter.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemProvinceBinding
import haina.ecommerce.model.property.DataProvince
import haina.ecommerce.preference.SharedPreferenceHelper

class AdapterListProvince(val context: Context, private var dataProvince: List<DataProvince?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListProvince.Holder>(), Filterable {

    private var listResult:List<DataProvince?>?=dataProvince

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemProvinceBinding.bind(view)
        fun bind(itemHaina: DataProvince, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvProvince.text = itemHaina.name
                tvProvince.setOnClickListener {
                    itemAdapterCallback.onClickAdapterProvince(tvProvince, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProvince.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProvinceBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProvince.Holder, position: Int) {
        val depart: DataProvince = dataProvince?.get(position)!!
        holder.bind(depart, itemAdapterCallback)

    }

    interface ItemAdapterCallback{
        fun onClickAdapterProvince(view: View, data:DataProvince)
    }

    override fun getItemCount(): Int = dataProvince!!.size

    override fun getFilter(): Filter {
        return object :Filter(){
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
                dataProvince = p1?.values as List<DataProvince?>?
                notifyDataSetChanged()
            }

        }
    }
}