package haina.ecommerce.adapter.flight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.model.flight.MealInfosItem

class AdapterAddOn(val context: Context, private val dataPassenger: List<MealInfosItem?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterAddOn.Holder>() {

    private var broadcaster: LocalBroadcastManager? = null
    var mCheckSum = 0
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemChooseAddOnBinding.bind(view)
        fun bind(itemHaina: MealInfosItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                val descAndPrice = "${itemHaina.desc} - ${itemHaina.fare}"
                cbAddon.text = descAndPrice
                cbAddon.setOnCheckedChangeListener { view, isChecked ->
                    if (isChecked){
                        mCheckSum += itemHaina.fare!!
//                        val intentDataPrice = Intent("priceAddOn")
//                        intentDataPrice.putExtra("price", mCheckSum)
//                        broadcaster?.sendBroadcast(intentDataPrice)
                        itemAdapterCallback.onClickAdapter(cbAddon, mCheckSum)
                    } else {
                        mCheckSum -= itemHaina.fare!!
//                        val intentDataPrice = Intent("priceAddOn")
//                        intentDataPrice.putExtra("price", mCheckSum)
//                        broadcaster?.sendBroadcast(intentDataPrice)
                        itemAdapterCallback.onClickAdapter(cbAddon, mCheckSum)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAddOn.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemChooseAddOnBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAddOn.Holder, position: Int) {
        val depart: MealInfosItem = dataPassenger?.get(position)!!
        holder.bind(depart, itemAdapterCallback)

    }

    interface ItemAdapterCallback{
        fun onClickAdapter(view: View, price:Int)
    }

    override fun getItemCount(): Int = dataPassenger!!.size
}