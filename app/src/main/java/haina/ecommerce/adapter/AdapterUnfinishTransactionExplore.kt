package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemTransactionPendingBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.transactionlist.PendingItem


class AdapterUnfinishTransactionExplore(val context: Context, private val listUnfinishTransaction: List<PendingItem?>?):
    RecyclerView.Adapter<AdapterUnfinishTransactionExplore.Holder>() {

    private val helper:Helper = Helper()

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemTransactionPendingBinding.bind(view)
        fun bind(itemHaina: PendingItem){
            with(binding){
                tvStatusTransaction.text = itemHaina.status
                tvTotalPayment.text = helper.convertToFormatMoneyIDRFilter(itemHaina.totalPayment.toString())
                tvDueDate.text = itemHaina.payment?.settlementTime.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUnfinishTransactionExplore.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTransactionPendingBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterUnfinishTransactionExplore.Holder, position: Int) {
        val list: PendingItem = listUnfinishTransaction?.get(position)!!
        holder.bind(list)
    }

    override fun getItemCount(): Int = listUnfinishTransaction?.size!!


}