package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.model.hotels.newHotel.SpecialRequestArrayItem

class AdapterSpecialRequestArray(val context: Context, private val listSpecialRequest: List<SpecialRequestArrayItem?>?,
                                 private val itemAdapterCallback: ItemAdapterCallback, private val statusEdit:Boolean) :
        RecyclerView.Adapter<AdapterSpecialRequestArray.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemChooseAddOnBinding.bind(view)
        fun bind(itemHaina: SpecialRequestArrayItem?, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                cbAddon.text = itemHaina?.description
                when(statusEdit){
                    true -> {
                        cbAddon.isEnabled = true
                        cbAddon.setOnCheckedChangeListener { _, b ->
                            if (b){
                                itemAdapterCallback.onClickSpecialRequest(cbAddon, itemHaina!!, true)
                            } else {
                                itemAdapterCallback.onClickSpecialRequest(cbAddon, itemHaina!!, false)
                            }
                        }
                    }
                    false -> {
                        cbAddon.isEnabled = false
                        cbAddon.isChecked = true
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSpecialRequestArray.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemChooseAddOnBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterSpecialRequestArray.Holder, position: Int) {
        val photo: SpecialRequestArrayItem? = listSpecialRequest?.get(position)
        holder.bind(photo, itemAdapterCallback)
    }

//    override fun getItemCount(): Int = listSpecialRequest.size
    override fun getItemCount():Int{
    return listSpecialRequest?.size ?: 0
    }

    interface ItemAdapterCallback{
        fun onClickSpecialRequest(view:View, dataRequest:SpecialRequestArrayItem, status:Boolean)
    }

}