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
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.model.transactionlist.PendingJobItem
import java.util.ArrayList

class AdapterTransactionPulsaUnfinish(
    val context: Context,
    private val listTransactionUnfinish: List<PendingItem?>?,
    private var itemAdapterCallBack: ItemAdapterCallback,
    private val listTransactionUnfinishJob : List<PendingJobItem?>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val helper:Helper = Helper

    companion object {
        var VIEW_TYPE = 1
    }

    inner class ViewHolderTransactionUnfinishPulsa(val binding: ListItemUnfinishTransactionBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: PendingItem?, itemAdapterCallBack: ItemAdapterCallback){
            with(binding){
                tvDateTransaction.text = itemHaina?.transactionTime
                tvDescriptionProduct.text = itemHaina?.product?.description
                tvDueDate.text = itemHaina?.payment?.paymentStatus
                when(itemHaina?.payment?.idPaymentMethod){
                    1 -> {
                        setLayoutVirtualAccount(binding, itemHaina.payment.vaNumber.toString(), itemHaina.totalPayment.toString())
                    }
                    2 -> {
                        setLayoutBankTransfer(binding, itemHaina.payment.vaNumber.toString())
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

    inner class ViewHolderTransactionUnfinishJob(val binding: ListItemUnfinishTransactionBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: PendingJobItem?, itemAdapterCallBack: ItemAdapterCallback){
            with(binding){
                tvDateTransaction.text = itemHaina?.transactionTime
                tvDescriptionProduct.text = itemHaina?.product
                tvDueDate.text = itemHaina?.status
                when(itemHaina?.idPaymentMethod){
                    1 -> {
                        setLayoutVirtualAccount(binding, itemHaina.vaNumber, itemHaina.totalPayment.toString())
                    }
                    2 -> {
                        setLayoutBankTransfer(binding, itemHaina.vaNumber)
                    }
                }

                copyVirtualAccount(binding)
                btnHowPay.setOnClickListener {
//                        itemAdapterCallBack.onClickAdapter(binding.btnHowPay, itemHaina)
                }

                ivOption.setOnClickListener {
//                        itemAdapterCallBack.onClickAdapter(binding.ivOption, itemHaina)
                }

                relativeClick.setOnClickListener {
//                        itemAdapterCallBack.onClickAdapter(binding.relativeClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                ViewHolderTransactionUnfinishPulsa(
                    ListItemUnfinishTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                ViewHolderTransactionUnfinishJob(
                    ListItemUnfinishTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transactionUnfinish: PendingItem? = listTransactionUnfinish?.get(position)
        val transactionJob : PendingJobItem? = listTransactionUnfinishJob?.get(position)
        when(VIEW_TYPE){
            1 -> {
                (holder as ViewHolderTransactionUnfinishPulsa).bind(transactionUnfinish!!, itemAdapterCallBack)
            }
            else -> {
                (holder as ViewHolderTransactionUnfinishJob).bind(transactionJob, itemAdapterCallBack)
            }
        }
//        holder.bind(transactionUnfinish, itemAdapterCallBack)
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
      return when (VIEW_TYPE){
          1 -> {
              listTransactionUnfinish!!.size
          }
          else -> {
              listTransactionUnfinishJob!!.size
          }
      }
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

    private fun setLayoutVirtualAccount(binding: ListItemUnfinishTransactionBinding, vaNumber: String, totalPayment: String) {
        binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
        binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
        binding.includeVirtualAccount.tvPaymentMethod.text = "Virtual Account"
        binding.includeVirtualAccount.tvVirtualAccountNumber.text = vaNumber
        binding.includeVirtualAccount.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(totalPayment)
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
        fun onClickAdapter(view: View, data: PendingItem?)
    }

    fun addTransactionPulsaPending(data: List<PendingItem?>?) {
        data?.let { (listTransactionUnfinish as ArrayList<PendingItem?>?)?.addAll(it) }
        notifyItemRangeInserted((listTransactionUnfinish?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addTransactionJobPending(data: List<PendingJobItem?>?) {
        data?.let { (listTransactionUnfinishJob as ArrayList<PendingJobItem?>?)?.addAll(it)}
        notifyItemRangeInserted((listTransactionUnfinishJob?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        when(VIEW_TYPE){
            1 -> {
                (listTransactionUnfinish as ArrayList<PendingItem?>?)?.clear()
            }
            else -> {
                (listTransactionUnfinishJob as ArrayList<PendingJobItem?>?)?.clear()
            }
        }
    }

}
