package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFinishTransactionBinding
import haina.ecommerce.model.transactionlist.ProcessItem
import haina.ecommerce.view.detailtransaction.DetailTransactionActivity


class AdapterTransactionPulsaFinish(val context: Context, private val listTransactionFinish: List<ProcessItem?>?):
    RecyclerView.Adapter<AdapterTransactionPulsaFinish.Holder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionPulsaFinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFinishTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionPulsaFinish.Holder, position: Int) {
        val transactionFinish: ProcessItem = listTransactionFinish?.get(position)!!
        holder.bind(transactionFinish)
    }

    override fun getItemCount(): Int = listTransactionFinish?.size!!
}