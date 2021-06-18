package haina.ecommerce.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAddOnChoosedBinding
import haina.ecommerce.databinding.ListItemAirlinesBinding
import haina.ecommerce.databinding.ListItemSetAddonBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.flight.*

class AdapterCombinePassengerAndFlight(val context: Context, private val dataPassenger: ArrayList<DataSetPassenger>,
                                       private val dataTicket:ArrayList<Ticket>,
                                       private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterCombinePassengerAndFlight.Holder>() {

    private var broadcaster:LocalBroadcastManager? =null
    private var helper:Helper = Helper
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemSetAddonBinding.bind(view)
        fun bind(itemHaina: DataSetPassenger, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
               tvTitlePassenger.text = itemHaina.title
                val fullname = "${itemHaina.first_name} ${itemHaina.last_name}"
                tvNamePassenger.text = fullname
                tvBirthdate.text = itemHaina.birth_date
                tvIdcardNumber.text = itemHaina.id_number
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCombinePassengerAndFlight.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSetAddonBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCombinePassengerAndFlight.Holder, position: Int) {
        val depart: DataSetPassenger = dataPassenger[position]
        holder.bind(depart, itemAdapterCallback)
    }

    override fun getItemCount(): Int = dataPassenger.size

    interface ItemAdapterCallback{

    }
}