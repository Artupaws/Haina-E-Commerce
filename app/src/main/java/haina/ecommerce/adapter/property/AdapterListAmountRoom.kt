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

class AdapterListAmountRoom(val context: Context, private var dataProvince: List<Int>, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListAmountRoom.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemProvinceBinding.bind(view)
        fun bind(itemHaina: Int, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvProvince.text = itemHaina.toString()
                tvProvince.setOnClickListener {
                    itemAdapterCallback.onClickAdapterAmount(tvProvince, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListAmountRoom.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProvinceBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListAmountRoom.Holder, position: Int) {
        val depart: Int = dataProvince[position]
        holder.bind(depart, itemAdapterCallback)

    }

    interface ItemAdapterCallback{
        fun onClickAdapterAmount(view: View, data:Int)
    }

    override fun getItemCount(): Int = dataProvince.size

}