package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.model.transactionlist.ProcessItem


class AdapterTransactionUnfinish(val context: Context, private val listTransactionUnfinish: List<PendingItem?>?):
    RecyclerView.Adapter<AdapterTransactionUnfinish.Holder>() {

    private var idPaymentMethod:Int? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemUnfinishTransactionBinding.bind(view)
        fun bind(itemHaina: PendingItem){
            with(binding){
                idPaymentMethod = itemHaina.payment?.idPaymentMethod
                tvDateTransaction.text = itemHaina.transactionTime
                tvDescriptionProduct.text = itemHaina.product?.description
                tvDueDate.text = itemHaina.payment?.settlementTime.toString()
                setLayoutPayment(idPaymentMethod!!, binding, itemHaina)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionUnfinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemUnfinishTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionUnfinish.Holder, position: Int) {
        val transactionUnfinish: PendingItem = listTransactionUnfinish?.get(position)!!
        holder.bind(transactionUnfinish)
    }

    override fun getItemCount(): Int = listTransactionUnfinish?.size!!

        private fun setLayoutPayment(idpaymentMethod:Int, binding: ListItemUnfinishTransactionBinding, data:PendingItem?){
        if (idpaymentMethod == 1){
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
            binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
            binding.includeVirtualAccount.tvPaymentMethod.text = "Virtual Account"
            binding.includeVirtualAccount.tvVirtualAccountNumber.text = data?.payment?.vaNumber
            binding.includeVirtualAccount.tvTotalPay.text = data?.totalPayment.toString()
        } else {
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.GONE
            binding.includeBankTransfer.linearBankTransfer.visibility = View.VISIBLE
//            binding.includeBankTransfer.tvPaymentMethod.text = paymentMethod
            binding.includeBankTransfer.tvTotalPay.text = data?.totalPayment.toString()
        }
    }

}