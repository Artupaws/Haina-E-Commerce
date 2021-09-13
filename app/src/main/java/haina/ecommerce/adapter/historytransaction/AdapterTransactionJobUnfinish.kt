package haina.ecommerce.adapter.historytransaction

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.model.transactionlist.PendingJobItem
import java.util.ArrayList

class AdapterTransactionJobUnfinish(
    val context: Context,
    private val listTransactionUnfinishJob : ArrayList<PendingJobItem?>?,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val helper:Helper = Helper

    inner class ViewHolderTransactionUnfinishJob(val binding: ListItemUnfinishTransactionBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: PendingJobItem?, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                tvDateTransaction.text = dateFormat(itemHaina?.transactionTime)
                tvDescriptionProduct.text = itemHaina?.product
                tvDueDate.text = itemHaina?.status
                ivIcon.text = context.getString(R.string.job)
                when(itemHaina?.idPaymentMethod){
                    1 -> {
                        setLayoutVirtualAccount(binding, itemHaina)
                    }
                    2 -> {
                        setLayoutBankTransfer(binding, itemHaina.vaNumber)
                    }
                }

                copyVirtualAccount(binding)
                btnHowPay.setOnClickListener {
//                        itemAdapterCallBack.onClickAdapter(binding.btnHowPay, itemHaina)
                }

                tvOptionMenu.setOnClickListener {
                    itemAdapterCallback.onTransactionJobClick(tvOptionMenu, itemHaina, adapterPosition, listTransactionUnfinishJob)
                }

                relativeClick.setOnClickListener {
//                        itemAdapterCallBack.onClickAdapter(binding.relativeClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderTransactionUnfinishJob(ListItemUnfinishTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transactionJob : PendingJobItem? = listTransactionUnfinishJob?.get(position)
        (holder as ViewHolderTransactionUnfinishJob).bind(transactionJob, itemAdapterCallback)
    }

    override fun getItemCount(): Int {
    return listTransactionUnfinishJob?.size ?: 0
    }

    private fun setLayoutPayment(
        binding: ListItemUnfinishTransactionBinding,
        idPaymentMethod: Int,
        vaNumber: String,
        totalPayment: String
    ) {
        Log.d("idPaymentMethod", idPaymentMethod.toString())
        when (idPaymentMethod) {
            1 -> {
                binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
                binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
                binding.includeVirtualAccount.tvPaymentMethod.text = "Virtual Account"
                binding.includeVirtualAccount.tvVirtualAccountNumber.text = vaNumber
                binding.includeVirtualAccount.tvTotalPay.text = totalPayment
            }
            else -> {
                binding.includeVirtualAccount.linearVirtualAccount.visibility = View.GONE
                binding.includeBankTransfer.linearBankTransfer.visibility = View.VISIBLE
//            binding.includeBankTransfer.tvPaymentMethod.text = paymentMethod
                binding.includeBankTransfer.tvTotalPay.text = totalPayment
            }
        }
    }

    private fun setLayoutVirtualAccount(binding: ListItemUnfinishTransactionBinding, itemHaina:PendingJobItem) {
        binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
        binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
        binding.includeVirtualAccount.tvPaymentMethod.text = "Virtual Account"
        if (itemHaina.status.lowercase().contains("process")){
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.GONE
            binding.btnHowPay.visibility = View.GONE
        }
        else{
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
            binding.includeVirtualAccount.tvVirtualAccountNumber.text = itemHaina.vaNumber
        }
        binding.includeVirtualAccount.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(itemHaina.totalPayment.toString())
    }

    private fun setLayoutBankTransfer(binding: ListItemUnfinishTransactionBinding, totalPayment: String) {
        binding.includeVirtualAccount.linearVirtualAccount.visibility = View.GONE
        binding.includeBankTransfer.linearBankTransfer.visibility = View.VISIBLE
//            binding.includeBankTransfer.tvPaymentMethod.text = paymentMethod
        binding.includeBankTransfer.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(totalPayment)
    }

    private fun copyVirtualAccount(binding: ListItemUnfinishTransactionBinding) {
        binding.includeVirtualAccount.cvCopyVirtualAccount.setOnClickListener {
            val myClipboard: ClipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("text", binding.includeVirtualAccount.tvVirtualAccountNumber.text)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(context, "Virtual Account Copied", Toast.LENGTH_SHORT).show()
        }
    }

    interface ItemAdapterCallback {
        fun onTransactionJobClick(view: View, data: PendingJobItem?, adapterPosition:Int, listTransaction:ArrayList<PendingJobItem?>?)
    }

    fun addTransactionJobPending(data: List<PendingJobItem?>?) {
        data?.let { (listTransactionUnfinishJob as ArrayList<PendingJobItem?>?)?.addAll(it)}
        notifyItemRangeInserted((listTransactionUnfinishJob?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        (listTransactionUnfinishJob as ArrayList<PendingJobItem?>?)?.clear()
    }

}
