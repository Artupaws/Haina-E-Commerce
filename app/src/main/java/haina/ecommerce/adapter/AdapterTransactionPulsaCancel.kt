package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCancelTransactionBinding
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.transactionlist.CancelItem
import haina.ecommerce.view.detailtransaction.DetailTransactionActivity


class AdapterTransactionPulsaCancel(val context: Context, private val listTransactionCancel: List<CancelItem?>?):
    RecyclerView.Adapter<AdapterTransactionPulsaCancel.Holder>() {

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
                tvDateTransaction.text = dateFormat(itemHaina.transactionTime)
                tvStatusTransaction.text = itemHaina.status
                tvTitleTransaction.text = itemHaina.product?.description
                tvCustomerNumber.text = itemHaina.customerNumber
                tvTotalPrice.text = itemHaina.totalPayment.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionPulsaCancel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCancelTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionPulsaCancel.Holder, position: Int) {
        val transactionCancel: CancelItem = listTransactionCancel?.get(position)!!
        holder.bind(transactionCancel)
    }

    override fun getItemCount(): Int = listTransactionCancel?.size!!


}