package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemPaymentMethodBinding
import haina.ecommerce.model.DataPaymentMethod


class AdapterPaymentMethod(val context: Context, private val list_payment_method: List<DataPaymentMethod?>?):
    RecyclerView.Adapter<AdapterPaymentMethod.Holder>() {

    var onItemClick: (String, String) -> Unit = {serviceFee: String, nameBank: String->  }

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPaymentMethodBinding.bind(view)
        fun bind(itemHaina: DataPaymentMethod){
            with(binding){
                tvNameBank.text = itemHaina.name
                linearPaymentMethod.setOnClickListener {
                    onItemClick(itemHaina.name, itemHaina.serviceFee)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPaymentMethod.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPaymentMethodBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPaymentMethod.Holder, position: Int) {
        val paymentMethod: DataPaymentMethod = list_payment_method?.get(position)!!
        holder.bind(paymentMethod)
    }

    override fun getItemCount(): Int = list_payment_method?.size!!


}