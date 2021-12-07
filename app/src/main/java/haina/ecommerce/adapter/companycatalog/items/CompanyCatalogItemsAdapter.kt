package haina.ecommerce.adapter.companycatalog.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.CompanyDashboardAdapter
import haina.ecommerce.databinding.ListItemCompanyCatalogItemsBinding
import haina.ecommerce.databinding.ListItemCompanyCategoryBinding
import haina.ecommerce.model.companycatalog.master.CompanyItem
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants


class CompanyCatalogItemsAdapter(val context: Context,
                                 private val listItem:ArrayList<CompanyItem?>?,
                                 val callback: ItemAdapterCallback)
    : RecyclerView.Adapter<CompanyCatalogItemsAdapter.Holder>() {

    private lateinit var sharedPref: SharedPreferenceHelper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCompanyCatalogItemsBinding.bind(view)
        fun bind(data: CompanyItem, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                cvItems.setOnClickListener { itemAdapterCallback.itemOnClick(data) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCompanyCatalogItemsBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data: CompanyItem = listItem?.get(position)!!
        holder.bind(data, callback)
    }

    override fun getItemCount(): Int = listItem!!.size

    interface ItemAdapterCallback{
        fun itemOnClick(data:CompanyItem)
    }

    fun add(data:List<CompanyItem?>?){
        listItem?.addAll(data!!)
        notifyItemRangeInserted((listItem?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listItem?.clear()
        notifyDataSetChanged()
    }
}