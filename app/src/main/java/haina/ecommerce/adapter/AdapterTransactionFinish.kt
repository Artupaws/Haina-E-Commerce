package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFinishTransactionBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.transactionlist.ProcessItem
import haina.ecommerce.model.transactionlist.SuccessItem
import haina.ecommerce.view.detailtransaction.DetailTransactionActivity


class AdapterTransactionFinish(val context: Context, private val listTransactionFinish: List<ProcessItem?>?):
    RecyclerView.Adapter<AdapterTransactionFinish.Holder>() {

    private var idPaymentMethod:Int? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFinishTransactionBinding.bind(view)
        fun bind(itemHaina: ProcessItem){
            with(binding){
                relativeClick.setOnClickListener {
                    val intent = Intent(context, DetailTransactionActivity::class.java)
                            .putExtra("transaction", "finish")
                            .putExtra("dataFinish", itemHaina)
                    context.startActivity(intent)
                }
                tvTitleService.text = itemHaina.product?.description
                tvDateTransaction.text = itemHaina.transactionTime
                tvStatusPayment.text = itemHaina.status
                tvTitleTransaction.text = itemHaina.product?.description
                tvCustomerNumber.text = itemHaina.customerNumber
                tvTotalPrice.text = itemHaina.totalPayment.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionFinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFinishTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionFinish.Holder, position: Int) {
        val transactionFinish: ProcessItem = listTransactionFinish?.get(position)!!
        holder.bind(transactionFinish)
    }

    override fun getItemCount(): Int = listTransactionFinish?.size!!
}