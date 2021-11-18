package haina.ecommerce.adapter.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFacilitiesRoomBinding
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.preference.SharedPreferenceHelper
import timber.log.Timber
import java.util.ArrayList

class AdapterRestaurantFilter(
    private val listCuisine: ArrayList<CuisineAndTypeData?>?,
    private val listType: ArrayList<CuisineAndTypeData?>?,
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var index:Int = -1
    private var listResultCuisine: ArrayList<CuisineAndTypeData?>? = listCuisine
    private var listResultType: ArrayList<CuisineAndTypeData?>? = listType

    companion object {
        var VIEW_TYPE = 1
    }

    inner class ViewHolderCuisine(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: CuisineAndTypeData, adapterCallback: Callback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    adapterCallback.listCuisineClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderType(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: CuisineAndTypeData, adapterCallback: Callback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    adapterCallback.listTypeClick(relativeClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (VIEW_TYPE) {
            1 -> {
                ViewHolderCuisine(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            2 -> {
                ViewHolderType(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {

                ViewHolderType(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (VIEW_TYPE) {
            1 -> {
                val cuisine: CuisineAndTypeData? = listResultCuisine?.get(position)
                (holder as ViewHolderCuisine).bind(cuisine!!, callback!!)
            }
            2 -> {
                val type: CuisineAndTypeData? = listResultType?.get(position)
                (holder as ViewHolderType).bind(type!!, callback!!)
            }
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return when (VIEW_TYPE) {
            1 -> {
                listResultCuisine?.size?:0
            }
            2 -> {
                listResultType?.size?:0
            }
            else -> {0}
        }
    }

    interface Callback {
        fun listCuisineClick(view: View, data: CuisineAndTypeData)
        fun listTypeClick(view: View, data: CuisineAndTypeData)
    }

    fun addCuisine(data: List<CuisineAndTypeData?>?) {
        data?.let { listCuisine?.addAll(it) }
        notifyItemRangeInserted((listType?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addType(data: List<CuisineAndTypeData?>?) {
        data?.let { listType?.addAll(it) }
        notifyItemRangeInserted((listType?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        when(VIEW_TYPE) {
            1 -> {
                listCuisine?.clear()
                listResultCuisine?.clear()
                notifyDataSetChanged()
            }
            2 -> {
                listType?.clear()
                listResultType?.clear()
                notifyDataSetChanged()
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.lowercase()
                Timber.d(querySearch)
                val filterResult = FilterResults()
                when (VIEW_TYPE) {
                    1 -> {
                        val result :MutableList<CuisineAndTypeData?>?
                        if (querySearch == null) {
                            result = listCuisine
                        } else {
                            result = listCuisine?.filter {
                                it?.name!!.lowercase().contains(querySearch,true)
                            }?.toMutableList()
                        }
                        filterResult.values = result
                    }
                    2 -> {
                        val result :MutableList<CuisineAndTypeData?>?
                        if (querySearch == null) {
                            result = listType
                        } else {
                            result = listType?.filter {
                                it?.name!!.lowercase().contains(querySearch,true)
                            }?.toMutableList()
                        }
                        filterResult.values = result
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                when (VIEW_TYPE) {
                    1 -> {
                        listResultCuisine = p1?.values as ArrayList<CuisineAndTypeData?>
                        notifyDataSetChanged()
                    }
                    2 -> {
                        listResultType = p1?.values as ArrayList<CuisineAndTypeData?>
                        notifyDataSetChanged()
                    }
                }
            }

        }
    }

}