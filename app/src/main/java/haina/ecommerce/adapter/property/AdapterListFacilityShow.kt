package haina.ecommerce.adapter.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAutotextcompleteBinding
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.model.property.DataFacilitiesProperty
import haina.ecommerce.model.property.FacilitiesItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class AdapterListFacilityShow(val context: Context, private val dataFacility: List<FacilitiesItem?>?):
        RecyclerView.Adapter<AdapterListFacilityShow.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemAutotextcompleteBinding.bind(view)
        fun bind(itemHaina: FacilitiesItem) {
            with(binding) {
                when(sharedPref.getValueString(Constants.LANGUAGE_APP)){
                    "en"-> {
                        tvName.text = itemHaina.facilityName
                    }
                    "zh"-> {
                        tvName.text = itemHaina.facilityNameZh
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListFacilityShow.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAutotextcompleteBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListFacilityShow.Holder, position: Int) {
        val depart: FacilitiesItem = dataFacility?.get(position)!!
        holder.bind(depart)

    }

    override fun getItemCount(): Int = dataFacility!!.size
}