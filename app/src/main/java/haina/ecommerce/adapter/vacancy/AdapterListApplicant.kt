package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemSubmitApplicationBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import java.util.ArrayList

class AdapterListApplicant(val context: Context,
                           private val listApplicant: ArrayList<DataListApplicant?>?, )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    companion object{
        var VIEW_TYPE = 1
    }

    inner class ViewHolderAllApplicant(val binding: ListItemSubmitApplicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataListApplicant) {
            with(binding) {
                tvNameApplicant.text = itemHaina.user?.fullname
                tvLastPosition.text = itemHaina.user?.workExperience?.position
                val dateStartExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val dateEndExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val totalExperience = (dateEndExperience?.toInt()?.minus(dateStartExperience?.toInt()!!))
                val companyAndExperience = "${itemHaina.user?.workExperience?.company}(${totalExperience})"
                tvLastCompanyAndExperience.text = companyAndExperience
                val expectedSalary = "Expected Salary : ${Helper.convertToFormatMoneyIDRFilter(itemHaina.user?.workExperience?.salary.toString())}"
                tvExpectedSalary.text = expectedSalary
                val lastEducation = "Last Education : ${itemHaina.user?.education?.degreeName}(${itemHaina.user?.education?.major})"
                tvLastEducation.text = lastEducation
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return when (VIEW_TYPE){
            1-> {
                ViewHolderAllApplicant(
                    ListItemSubmitApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                ViewHolderAllApplicant(
                    ListItemSubmitApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val allApplicant: DataListApplicant? = listApplicant?.get(position)
        when(VIEW_TYPE){
            1-> {
                allApplicant?.let { (holder as ViewHolderAllApplicant).bind(it) }
            }
        }
    }

    override fun getItemCount(): Int {
        return when(VIEW_TYPE){
            1->{
                listApplicant?.size?:0
            }
            else -> {
                listApplicant?.size?:0
            }
        }
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    interface AdapterCallbackMyVacancy {
        fun listMyVacancy(view: View, dataMyVacancy: DataMyVacancy, buttonState:Int?)
    }

    fun addListApplicant(data: List<DataListApplicant?>?) {
        data?.let { listApplicant?.addAll(it) }
        notifyItemRangeInserted((listApplicant?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        when(VIEW_TYPE){
            1-> {
                listApplicant?.clear()
                notifyDataSetChanged()
            }
        }
    }

}