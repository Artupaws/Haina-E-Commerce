package haina.ecommerce.adapter.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.model.property.DataFacilitiesProperty
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class AdapterListFacility(val context: Context, private val dataFacility: List<DataFacilitiesProperty?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListFacility.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemChooseAddOnBinding.bind(view)
        fun bind(itemHaina: DataFacilitiesProperty, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                when(sharedPref.getValueString(Constants.LANGUAGE_APP)){
                    "en"-> {
                        cbAddon.text = itemHaina.name
                    }
                    "zh"-> {
                        cbAddon.text = itemHaina.nameZh
                    }
                }
                cbAddon.setOnCheckedChangeListener{ _, b ->
                    if (b){
                        itemAdapterCallback.onClickAdapter(cbAddon, itemHaina, true)
                    } else {
                        itemAdapterCallback.onClickAdapter(cbAddon, itemHaina, false)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListFacility.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemChooseAddOnBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListFacility.Holder, position: Int) {
        val depart: DataFacilitiesProperty = dataFacility?.get(position)!!
        holder.bind(depart, itemAdapterCallback)

    }

    interface ItemAdapterCallback{
        fun onClickAdapter(view: View, data:DataFacilitiesProperty, status:Boolean)
    }

    override fun getItemCount(): Int = dataFacility!!.size
}