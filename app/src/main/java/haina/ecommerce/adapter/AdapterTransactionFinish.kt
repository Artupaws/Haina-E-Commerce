package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFinishTransactionBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.transactionlist.SuccessItem


class AdapterTransactionFinish(val context: Context, private val listTransactionFinish: List<SuccessItem?>?):
    RecyclerView.Adapter<AdapterTransactionFinish.Holder>() {

    private var idPaymentMethod:Int? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFinishTransactionBinding.bind(view)
        fun bind(itemHaina: SuccessItem){
            with(binding){
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
        val binding = ListItemUnfinishTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionFinish.Holder, position: Int) {
        val transactionFinish: SuccessItem = listTransactionFinish?.get(position)!!
        holder.bind(transactionFinish)
    }

    override fun getItemCount(): Int = listTransactionFinish?.size!!


}