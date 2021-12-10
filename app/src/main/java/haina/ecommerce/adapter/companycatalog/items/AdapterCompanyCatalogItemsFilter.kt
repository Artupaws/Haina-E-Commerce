package haina.ecommerce.adapter.companycatalog.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.adapter.restaurant.AdapterRestaurantFilter
import haina.ecommerce.databinding.ListItemFacilitiesRoomBinding
import haina.ecommerce.model.Company
import haina.ecommerce.model.companycatalog.master.CompanyItem
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import timber.log.Timber
import java.util.ArrayList

class AdapterCompanyCatalogItemsFilter(
    private val listCategory: ArrayList<CompanyItemCategory?>?,
    private val callback: Callback
): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable  {

    var index:Int = -1
    private var listResultCategory: ArrayList<CompanyItemCategory?>? = listCategory


    inner class ViewHolder(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CompanyItemCategory, adapterCallback: Callback) {
            with(binding) {
                tvNameFacilities.text = data.name
                relativeClick.setOnClickListener {
                    adapterCallback.CategoryClick( data)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemFacilitiesRoomBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data: CompanyItemCategory? = listResultCategory?.get(position)
        (holder as ViewHolder).bind(data!!, callback!!)
    }


    override fun getItemCount(): Int = listResultCategory!!.size

    interface Callback {
        fun CategoryClick(data: CompanyItemCategory)
    }

    fun add(data: List<CompanyItemCategory?>?) {
        data?.let { listCategory?.addAll(it) }
        notifyItemRangeInserted((listCategory?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listCategory?.clear()
        listResultCategory?.clear()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.lowercase()
                Timber.d(querySearch)
                val filterResult = FilterResults()

                val result :MutableList<CompanyItemCategory?>?
                if (querySearch == null) {
                    result = listCategory
                } else {
                    result = listCategory?.filter {
                        it?.name!!.lowercase().contains(querySearch,true)
                    }?.toMutableList()
                }
                filterResult.values = result
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                listResultCategory = p1?.values as ArrayList<CompanyItemCategory?>
                notifyDataSetChanged()
            }

        }
    }
}