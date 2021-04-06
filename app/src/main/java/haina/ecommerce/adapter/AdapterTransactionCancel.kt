package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCancelTransactionBinding
import haina.ecommerce.databinding.ListItemFinishTransactionBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.transactionlist.CancelItem
import haina.ecommerce.model.transactionlist.SuccessItem
import haina.ecommerce.view.detailtransaction.DetailTransactionActivity


class AdapterTransactionCancel(val context: Context, private val listTransactionCancel: List<CancelItem?>?):
    RecyclerView.Adapter<AdapterTransactionCancel.Holder>() {

    private var idPaymentMethod:Int? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCancelTransactionBinding.bind(view)
        fun bind(itemHaina: CancelItem){
            with(binding){
                relativeClick.setOnClickListener {
                    val intent = Intent(context, DetailTransactionActivity::class.java)
                            .putExtra("transaction", "cancel")
                            .putExtra("dataCancel", itemHaina)
                    context.startActivity(intent)
                }
                tvTitleService.text = itemHaina.product?.description
                tvDateTransaction.text = itemHaina.transactionTime
                tvStatusTransaction.text = itemHaina.status
                tvTitleTransaction.text = itemHaina.product?.description
                tvCustomerNumber.text = itemHaina.customerNumber
                tvTotalPrice.text = itemHaina.totalPayment.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionCancel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCancelTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionCancel.Holder, position: Int) {
        val transactionCancel: CancelItem = listTransactionCancel?.get(position)!!
        holder.bind(transactionCancel)
    }

    override fun getItemCount(): Int = listTransactionCancel?.size!!


}