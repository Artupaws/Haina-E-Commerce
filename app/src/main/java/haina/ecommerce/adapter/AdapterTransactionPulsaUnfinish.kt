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
import haina.ecommerce.model.transactionlist.PendingItem

class AdapterTransactionPulsaUnfinish(
    val context: Context,
    private val listTransactionUnfinish: List<PendingItem?>?,
    private var itemAdapterCallBack: ItemAdapterCallback
) :
    RecyclerView.Adapter<AdapterTransactionPulsaUnfinish.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemUnfinishTransactionBinding.bind(view)
        fun bind(itemHaina: PendingItem, itemAdapterCallBack: ItemAdapterCallback) {
            with(binding) {
                tvDateTransaction.text = itemHaina.transactionTime
                tvDescriptionProduct.text = itemHaina.product?.description
                tvDueDate.text = itemHaina.payment?.paymentStatus
                Log.d(
                    "dataTransaction",
                    "${itemHaina.payment?.vaNumber}, ${itemHaina.payment?.idPaymentMethod}"
                )
                when(itemHaina.payment?.idPaymentMethod){
                    1 -> {
                        setLayoutVirtualAccount(binding, itemHaina.payment.vaNumber.toString(), itemHaina.totalPayment.toString())
                    }
                    2 -> {
                        setLayoutBankTransfer(binding, itemHaina.payment?.vaNumber.toString(), itemHaina.totalPayment.toString())
                    }
                }

                copyVirtualAccount(binding)
                btnHowPay.setOnClickListener {
                    itemAdapterCallBack.onClickAdapter(binding.btnHowPay, itemHaina)
                }

                ivOption.setOnClickListener {
                    itemAdapterCallBack.onClickAdapter(binding.ivOption, itemHaina)
                }

                relativeClick.setOnClickListener {
                    itemAdapterCallBack.onClickAdapter(binding.relativeClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterTransactionPulsaUnfinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemUnfinishTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionPulsaUnfinish.Holder, position: Int) {
        val transactionUnfinish: PendingItem = listTransactionUnfinish?.get(position)!!
        holder.bind(transactionUnfinish, itemAdapterCallBack)
    }

    override fun getItemCount(): Int = listTransactionUnfinish?.size!!

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

    private fun setLayoutVirtualAccount(binding: ListItemUnfinishTransactionBinding, vaNumber: String, totalPayment: String) {
        binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
        binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
        binding.includeVirtualAccount.tvPaymentMethod.text = "Virtual Account"
        binding.includeVirtualAccount.tvVirtualAccountNumber.text = vaNumber
        binding.includeVirtualAccount.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(totalPayment)
    }

    private fun setLayoutBankTransfer(binding: ListItemUnfinishTransactionBinding, vaNumber: String, totalPayment: String) {
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
        fun onClickAdapter(view: View, data: PendingItem)
    }

}