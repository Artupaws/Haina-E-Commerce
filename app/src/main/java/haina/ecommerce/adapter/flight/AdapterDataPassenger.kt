package haina.ecommerce.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemDataPassengerBinding
import haina.ecommerce.room.roomdatapassenger.DataPassenger

class AdapterDataPassenger(val context: Context, private val listDataPassenger: ArrayList<DataPassenger>,
                           private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterDataPassenger.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemDataPassengerBinding.bind(view)
        fun bind(itemHaina: DataPassenger, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                val namePassenger = "${itemHaina.last_name}, ${itemHaina.first_name}"
                tvTitlePassenger.text = itemHaina.title
               tvNamePassenger.text = namePassenger
                tvBirthdate.text = itemHaina.birth_date
                if (itemHaina.id_number == null){
                    tvIdcardNumber.visibility = View.GONE
                } else {
                    tvIdcardNumber.visibility = View.VISIBLE
                }
                tvIdcardNumber.text = itemHaina.id_number
                tvActionEdit.setOnClickListener { itemAdapterCallback.onClick(binding.tvActionEdit, itemHaina) }
                tvActionDelete.setOnClickListener { itemAdapterCallback.onClick(binding.tvActionDelete, itemHaina) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDataPassenger.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDataPassengerBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterDataPassenger.Holder, position: Int) {
        val photo: DataPassenger = listDataPassenger[position]
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listDataPassenger.size

    interface ItemAdapterCallback{
        fun onClick(view:View, data: DataPassenger)
    }

}