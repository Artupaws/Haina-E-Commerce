package haina.ecommerce.adapter

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
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.model.transactionlist.PendingJobItem
import java.util.ArrayList

class AdapterTransactionPulsaUnfinish(
    val context: Context,
    private val listTransactionUnfinish: ArrayList<PendingItem?>?,
    private var itemAdapterCallBack: ItemAdapterCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val helper:Helper = Helper

    companion object {
        var VIEW_TYPE = 1
    }

    inner class ViewHolderTransactionUnfinishPulsa(val binding: ListItemUnfinishTransactionBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: PendingItem?, itemAdapterCallBack: ItemAdapterCallback){
            with(binding){
                tvDateTransaction.text = dateFormat(itemHaina?.transactionTime)
                tvDescriptionProduct.text = itemHaina?.product?.description
                tvDueDate.text = itemHaina?.status
                when(itemHaina?.payment?.idPaymentMethod){
                    1 -> {
                        setLayoutVirtualAccount(binding, itemHaina)
                    }
                    2 -> {
                        setLayoutBankTransfer(binding, itemHaina.payment.vaNumber.toString())
                    }
                }
                when(itemHaina?.status?.lowercase()){
                    "pending payment" -> {
                        tvOptionMenu.visibility = View.VISIBLE
                    }
                    else -> {
                        tvOptionMenu.visibility = View.GONE
                    }
                }
                copyVirtualAccount(binding)
                btnHowPay.setOnClickListener {
                    itemAdapterCallBack.onClickAdapter(binding.btnHowPay, itemHaina, adapterPosition, listTransactionUnfinish)
                }

                tvOptionMenu.setOnClickListener {
                    itemAdapterCallBack.onClickAdapter(tvOptionMenu, itemHaina, adapterPosition, listTransactionUnfinish)
                }

                relativeClick.setOnClickListener {
                    itemAdapterCallBack.onClickAdapter(binding.relativeClick, itemHaina, adapterPosition, listTransactionUnfinish)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderTransactionUnfinishPulsa(
                    ListItemUnfinishTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transactionUnfinish: PendingItem? = listTransactionUnfinish?.get(position)
        (holder as ViewHolderTransactionUnfinishPulsa).bind(transactionUnfinish, itemAdapterCallBack)
    }

    override fun getItemCount(): Int {
        return listTransactionUnfinish?.size ?: 0
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

    private fun setLayoutVirtualAccount(binding: ListItemUnfinishTransactionBinding, itemHaina: PendingItem?) {
        binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
        binding.includeVirtualAccount.tvPaymentMethod.text = "Virtual Account"
        binding.includeVirtualAccount.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(itemHaina?.totalPayment.toString())
        if (itemHaina?.status?.lowercase()?.contains("process") == true){
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.GONE
            binding.btnHowPay.visibility = View.GONE
        }
        else{
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
            binding.includeVirtualAccount.tvVirtualAccountNumber.text = itemHaina?.payment?.vaNumber
        }
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
        fun onClickAdapter(view: View, data: PendingItem?, adapterPosition:Int, listTransaction:ArrayList<PendingItem?>?)
    }

    fun addTransactionPulsaPending(data: List<PendingItem?>?) {
        data?.let { (listTransactionUnfinish as ArrayList<PendingItem?>?)?.addAll(it) }
        notifyItemRangeInserted((listTransactionUnfinish?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        (listTransactionUnfinish as ArrayList<PendingItem?>?)?.clear()
    }

}
