package haina.ecommerce.adapter.property

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemShowPropertyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataCity
import haina.ecommerce.model.property.DataShowProperty

class AdapterShowProperty(val context: Context, private var dataProperty: List<DataShowProperty?>?,
                          private val itemAdapterCallback: ItemAdapterCallback,
                          private val showOnPublic:Boolean) :
        RecyclerView.Adapter<AdapterShowProperty.Holder>(), Filterable {

    private var listResultProperty : List<DataShowProperty?>? = dataProperty
    private val helper:Helper=Helper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemShowPropertyBinding.bind(view)
        fun bind(itemHaina: DataShowProperty, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                listParams = ArrayList()
                when(showOnPublic){
                    true -> {
                        tvStatus.visibility = View.GONE
                        tvExpiryDate.visibility = View.GONE
                    } false -> {
                    tvStatus.visibility = View.VISIBLE
                    tvStatus.text = itemHaina.status
                    val expiryDate = "Expired on ${itemHaina.expiryDate?.substring(0,10)}"
                    tvExpiryDate.text = expiryDate
                    }
                }
                if (itemHaina.images != null){
                    for (i in itemHaina.images) {
                        i?.path?.let { listParams.add(it) }
                        Log.d("listImageProperty", listParams.toString())
                        vpImageProperty.pageCount = listParams.size
                    }
                    imagesListener = ImageListener { _, imageView ->
                        Glide.with(context).load(listParams[0]).placeholder(R.drawable.ps5).into(imageView)
                    }
                    vpImageProperty.setImageListener(imagesListener)
                    vpImageProperty.setImageListener(imagesListener)
                }
                showPrice(binding, itemHaina)
                val condition = "Condition : ${itemHaina.condition}"
                tvConditionProperty.text = condition
                tvNameProperty.text = itemHaina.title
                tvYear.text = itemHaina.year
                val address = "${itemHaina.city.province}, ${itemHaina.city.nameCity}"
                tvLocation.text = address
                cvClick.setOnClickListener {
                    itemAdapterCallback.onClickAdapterCity(cvClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterShowProperty.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemShowPropertyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterShowProperty.Holder, position: Int) {
        val depart: DataShowProperty = dataProperty?.get(position)!!
        holder.bind(depart, itemAdapterCallback)

    }

    interface ItemAdapterCallback{
        fun onClickAdapterCity(view: View, data:DataShowProperty)
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
                dataProperty = p1?.values as List<DataShowProperty?>?
                notifyDataSetChanged()
            }

        }
    }

    private fun showPrice(binding:ListItemShowPropertyBinding, data:DataShowProperty){
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