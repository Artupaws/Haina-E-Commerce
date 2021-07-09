package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFinishTransactionBinding
import haina.ecommerce.model.transactionlist.ProcessItem
import haina.ecommerce.model.transactionlist.SuccessItem


class AdapterTransactionPulsaFinish(val context: Context, private val listTransactionFinish: List<SuccessItem?>?,
private val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterTransactionPulsaFinish.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFinishTransactionBinding.bind(view)
        fun bind(itemHaina: SuccessItem, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                relativeClick.setOnClickListener {
                    itemAdapterCallback.onClickAdapter(binding.relativeClick, itemHaina)
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
        val transactionFinish: SuccessItem = listTransactionFinish?.get(position)!!
        holder.bind(transactionFinish, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listTransactionFinish?.size!!

    interface ItemAdapterCallback{
        fun onClickAdapter(view: View, data:SuccessItem)
    }
}