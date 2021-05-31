package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemTransactionPendingBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.transactionlist.DataAllTransactionPending
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity


class AdapterUnfinishTransactionExplore(val context: Context, private val listUnfinishTransaction: List<DataAllTransactionPending?>?):
    RecyclerView.Adapter<AdapterUnfinishTransactionExplore.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemTransactionPendingBinding.bind(view)
        fun bind(itemHaina: DataAllTransactionPending){
            with(binding){
                val icon = HtmlCompat.fromHtml("${itemHaina.icon}", HtmlCompat.FROM_HTML_MODE_LEGACY)
                ivIconMethodPayment.text = icon
                tvStatusTransaction.text = itemHaina.product
                relativeClick.setOnClickListener {
                    val intent = Intent(context, HistoryTransactionActivity::class.java)
                    context.startActivity(intent)
                }
                tvTotalPayment.text = helper.convertToFormatMoneyIDRFilter(itemHaina.totalAmount.toString())
                tvDueDate.text = itemHaina.paymentMethod
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUnfinishTransactionExplore.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTransactionPendingBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterUnfinishTransactionExplore.Holder, position: Int) {
        val list: DataAllTransactionPending = listUnfinishTransaction?.get(position)!!
        holder.bind(list)
    }

    override fun getItemCount(): Int = listUnfinishTransaction?.size!!


}