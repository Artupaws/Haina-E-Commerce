package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemMyVacancyBinding
import haina.ecommerce.helper.Helper.convertToFormatMoneyIDRFilter
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import java.util.ArrayList

class AdapterMyVacancy(val context: Context,
                       private val listMyVacancy: ArrayList<DataMyVacancy?>?, private val adapterCallbackMyVacancy:AdapterCallbackMyVacancy, ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    inner class ViewHolderMyVacancy(val binding: ListItemMyVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataMyVacancy, adapterCallbackMyVacancy: AdapterCallbackMyVacancy) {
            with(binding) {
                binding.tvPositionJob.text = itemHaina.position
                if (itemHaina.deletedAt!=null){
                    binding.tvDateEnd.text = "Expired On: ${dateFormat(itemHaina.deletedAt)}"
                    binding.tvDateEnd.setTextColor(context.resources.getColor(android.R.color.black))
                }
                if (itemHaina.status == "pending"){
                    binding.tvDateEnd.text = "Please do payment for showing this ads"
                    binding.tvDateEnd.setTextColor(context.resources.getColor(android.R.color.holo_red_dark))
                    binding.btnShowAgain.text = "make payment"
                    binding.btnShowAgain.visibility = View.VISIBLE
                    binding.btnShowAgain.setOnClickListener {
                        adapterCallbackMyVacancy.listMyVacancy(btnShowAgain, itemHaina, 2)
                    }
                }
                binding.tvPackageName.text = itemHaina.packageName
                binding.tvStatusVacancy.text = itemHaina.status
                binding.tvApply.text = itemHaina.totalApplicant.toString()
                binding.tvChoosed.text = itemHaina.shortlistedApplicant.toString()
                binding.tvInterview.text = itemHaina.interviewApplicant.toString()
                if (itemHaina.salaryDisplay == 1)
                    binding.tvSalary.text = "${convertToFormatMoneyIDRFilter(itemHaina.minSalary.toString())} - ${convertToFormatMoneyIDRFilter(itemHaina.maxSalary.toString())}"
                else
                    binding.tvSalary.text = "${convertToFormatMoneyIDRFilter(itemHaina.minSalary.toString())} - ${convertToFormatMoneyIDRFilter(itemHaina.maxSalary.toString())} - hidden for user"
                relativeClick.setOnClickListener {
                    adapterCallbackMyVacancy.listMyVacancy(relativeClick, itemHaina, null)
                }
                linearApply.setOnClickListener {
                    adapterCallbackMyVacancy.listMyVacancy(linearApply, itemHaina, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return ViewHolderMyVacancy(
            ListItemMyVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level: DataMyVacancy? = listMyVacancy?.get(position)
        (holder as ViewHolderMyVacancy).bind(level!!, adapterCallbackMyVacancy)
    }

    override fun getItemCount(): Int {
        return listMyVacancy!!.size

    }

    interface AdapterCallbackMyVacancy {
        fun listMyVacancy(view: View, dataMyVacancy: DataMyVacancy, buttonState:Int?)
    }

    fun addVacancyLevel(data: List<DataMyVacancy?>?) {
        data?.let { listMyVacancy?.addAll(it) }
        notifyItemRangeInserted((listMyVacancy?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listMyVacancy?.clear()
        notifyDataSetChanged()
    }

}