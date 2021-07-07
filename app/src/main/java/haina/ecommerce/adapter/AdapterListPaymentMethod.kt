package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListPaymentMethodBinding
import haina.ecommerce.model.paymentmethod.PaymentmethodItem


class AdapterListPaymentMethod(val context: Context, private val list_payment_method: List<PaymentmethodItem?>?):
    RecyclerView.Adapter<AdapterListPaymentMethod.Holder>() {

    var onItemClick: (String, String) -> Unit = { _: String, _: String->  }
    var idPaymentMethod:Int? = null
    var broadcaster:LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListPaymentMethodBinding.bind(view)
        fun bind(itemHaina: PaymentmethodItem){
            with(binding){
                tvNameBank.text = itemHaina.name
                linearPaymentMethod.setOnClickListener {
                    val intentIdPaymentMethod = Intent("paymentMethod")
                            .putExtra("idPayment", itemHaina.id)
                            .putExtra("bankName", itemHaina.name)
                    broadcaster?.sendBroadcast(intentIdPaymentMethod)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListPaymentMethod.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListPaymentMethodBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListPaymentMethod.Holder, position: Int) {
        val paymentMethod: PaymentmethodItem = list_payment_method?.get(position)!!
        holder.bind(paymentMethod)
    }

    override fun getItemCount(): Int = list_payment_method?.size!!


}