package haina.ecommerce.adapter.companycatalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.photogallery.AdapterRestaurantPhotoGallery
import haina.ecommerce.databinding.ListItemCompanyCategoryBinding
import haina.ecommerce.databinding.ListItemRestaurantPhotoBinding
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPhoto
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber

class CompanyDashboardAdapter(val context: Context,
                              private val listItemCategory:ArrayList<CompanyItemCategory?>?,
                              val itemAdapterCallback: ItemAdapterCallback
) : RecyclerView.Adapter<CompanyDashboardAdapter.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCompanyCategoryBinding.bind(view)
        fun bind(data: CompanyItemCategory, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                val icon = HtmlCompat.fromHtml(data.iconCode!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
                ivIconCategory.text = icon
                when (sharedPref.getValueString(Constants.LANGUAGE_APP)){
                    "zh" -> {
                        binding.tvItemCategoryName.text = data.nameZh
                    }
                    "en" -> {
                        binding.tvItemCategoryName.text = data.name
                    }
                }
                llItemCategory.setOnClickListener { itemAdapterCallback.categoryOnClick(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCompanyCategoryBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data: CompanyItemCategory = listItemCategory?.get(position)!!
        holder.bind(data, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listItemCategory!!.size

    interface ItemAdapterCallback{
        fun categoryOnClick(data:CompanyItemCategory)
    }

    fun add(data:List<CompanyItemCategory?>?){
        listItemCategory?.addAll(data!!)
        notifyItemRangeInserted((listItemCategory?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listItemCategory?.clear()
        notifyDataSetChanged()
    }

}