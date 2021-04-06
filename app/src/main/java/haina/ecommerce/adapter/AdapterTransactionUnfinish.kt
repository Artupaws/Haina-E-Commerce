package haina.ecommerce.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.Login
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment
import haina.ecommerce.view.login.LoginActivity


class AdapterTransactionUnfinish(val context: Context, private val listTransactionUnfinish: List<PendingItem?>?) :
        RecyclerView.Adapter<AdapterTransactionUnfinish.Holder>(), BottomSheetHowToPayment.ItemClickListener {

    private var idPaymentMethod: Int? = null
    var onItemClick: (Int) -> Unit = { i: Int -> }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemUnfinishTransactionBinding.bind(view)
        fun bind(itemHaina: PendingItem) {
            with(binding) {
                idPaymentMethod = itemHaina.payment?.idPaymentMethod
                tvDateTransaction.text = itemHaina.transactionTime
                tvDescriptionProduct.text = itemHaina.product?.description
                tvDueDate.text = itemHaina.payment?.settlementTime.toString()
                setLayoutPayment(idPaymentMethod!!, binding, itemHaina)
                copyVirtualAccount(binding)
                btnHowPay.setOnClickListener {
                    onItemClick(adapterPosition)
                }
                ivOption.setOnClickListener {
                    val popup = PopupMenu(context, ivOption)
                    popup.inflate(R.menu.menu_cancel_transaction)
                        popup.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.action_cancel_transaction ->{
                                    Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
                                    true
                                } else -> false

                            }
                        }
                        popup.show()
                }
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

    private fun setLayoutPayment(idpaymentMethod: Int, binding: ListItemUnfinishTransactionBinding, data: PendingItem?) {
        if (idpaymentMethod == 1) {
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

    private fun copyVirtualAccount(binding: ListItemUnfinishTransactionBinding) {
        binding.includeVirtualAccount.cvCopyVirtualAccount.setOnClickListener {
            val myClipboard: ClipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager;
            val myClip = ClipData.newPlainText("text", binding.includeVirtualAccount.tvVirtualAccountNumber.text)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(context, "Virtual Account Copied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClick(item: String) {
        when (item) {
            "" -> {

            }
        }
    }

}