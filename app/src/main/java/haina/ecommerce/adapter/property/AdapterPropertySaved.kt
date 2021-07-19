package haina.ecommerce.adapter.property

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemPropertySavedBinding
import haina.ecommerce.databinding.ListItemShowPropertyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataCity
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.room.roomsavedproperty.DataSavedProperty
import haina.ecommerce.room.roomsavedproperty.RoomDataSavedProperty
import haina.ecommerce.room.roomsavedproperty.SavedPropertyDao

class AdapterPropertySaved(val context: Context, private var dataProperty: List<DataSavedProperty?>?,
                           private val itemAdapterCallback: ItemAdapterCallback,) :
        RecyclerView.Adapter<AdapterPropertySaved.Holder>(), Filterable {

    private var listResultProperty : List<DataSavedProperty?>? = dataProperty
    private val helper:Helper=Helper
    private lateinit var dao: SavedPropertyDao
    private lateinit var database: RoomDataSavedProperty

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemPropertySavedBinding.bind(view)
        fun bind(itemHaina: DataSavedProperty, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                showPrice(binding, itemHaina)
                tvTitleProperty.text = itemHaina.title
                tvLocation.text = itemHaina.address
                linearList.setOnClickListener {
                    itemAdapterCallback.onClickAdapterCity(linearList, itemHaina)
                }
                Glide.with(context).load(itemHaina.photo).into(ivProperty)
                tvOptionMenu.setOnClickListener {
                    val popup = PopupMenu(context, tvOptionMenu)
                    popup.inflate(R.menu.menu_option_delete_item)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.action_delete -> {
                                deleteProperty(DataSavedProperty(itemHaina.id,
                                    itemHaina.photo, itemHaina.sellingPrice, itemHaina.title,
                                    itemHaina.address, itemHaina.rentalPrice))
                                notifyItemRemoved(adapterPosition)
                            }
                        }
                        false
                    }
                    popup.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPropertySaved.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPropertySavedBinding.inflate(inflater)
        database = RoomDataSavedProperty.getDatabase(context)
        dao = database.getDataPropertyDao()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPropertySaved.Holder, position: Int) {
        val depart: DataSavedProperty = dataProperty?.get(position)!!
        holder.bind(depart, itemAdapterCallback)
    }

    interface ItemAdapterCallback{
        fun onClickAdapterCity(view: View, data:DataSavedProperty)
    }

    private fun deleteProperty(dataProperty:DataSavedProperty){
        dao.delete(dataProperty)
    }

    override fun getItemCount(): Int = dataProperty!!.size

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null){
                    listResultProperty
                } else {
                    listResultProperty?.filter {
                        it?.title?.toLowerCase()!!.contains(querySearch,ignoreCase = true)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                dataProperty = p1?.values as List<DataSavedProperty?>?
                notifyDataSetChanged()
            }

        }
    }

    private fun showPrice(binding:ListItemPropertySavedBinding, data:DataSavedProperty){
        if (data.rentalPrice.toString() == "0" && data.sellingPrice.toString() != "0"){
            binding.tvPriceSell.text = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            binding.linearPriceRent.visibility = View.GONE
        } else if (data.rentalPrice.toString() != "0" && data.sellingPrice.toString() == "0"){
            binding.tvPriceRent.text = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.linearPriceSell.visibility = View.GONE
        } else {
            val priceSell = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            val priceRent = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.tvPriceSell.text =  priceSell
            binding.tvPriceRent.text =  priceRent
        }

    }
}