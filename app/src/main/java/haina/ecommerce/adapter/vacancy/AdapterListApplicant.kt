package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemSubmitApplicationBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import java.util.ArrayList

class AdapterListApplicant(val context: Context,
                           private val listApplicant: ArrayList<DataListApplicant?>?,
                           private val adapterListApplicantCallback: AdapterListApplicantCallback)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    companion object{
        var VIEW_TYPE = 1
    }

    inner class ViewHolderAllApplicant(private val binding: ListItemSubmitApplicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataListApplicant, adapterListApplicantCallback: AdapterListApplicantCallback) {
            with(binding) {
                linearButton1.visibility = View.VISIBLE
                linearButton2.visibility = View.GONE
                tvNameApplicant.text = itemHaina.user?.fullname
                tvLastPosition.text = itemHaina.user?.workExperience?.position
                val dateStartExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val dateEndExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val totalExperience = (dateEndExperience?.toInt()?.minus(dateStartExperience?.toInt()!!))
                val companyAndExperience = "${itemHaina.user?.workExperience?.company}(${totalExperience} Year(s))"
                tvLastCompanyAndExperience.text = companyAndExperience
                if (itemHaina.user?.workExperience == null){
                    tvLastCompanyAndExperience.visibility = View.GONE
                    tvLastPosition.text = context.getString(R.string.no_work_experience)
                    tvExpectedSalary.visibility = View.GONE
                } else {
                    val expectedSalary = "${context.getString(R.string.last_salary)} : ${Helper.convertToFormatMoneyIDRFilter(itemHaina.user.workExperience.salary.toString())}"
                    tvExpectedSalary.text = expectedSalary
                }
                val lastEducation = "${context.getString(R.string.last_education)} : ${itemHaina.user?.education?.degreeName}-${itemHaina.user?.education?.major}"
                tvLastEducation.text = lastEducation
                btnReject.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnReject, adapterPosition, itemHaina, listApplicant)
                }
                btnShortlisted.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnShortlisted, adapterPosition, itemHaina, listApplicant)
                }
                btnInterview.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnInterview, adapterPosition, itemHaina, listApplicant)
                }
            }
        }
    }

    inner class ViewHolderApplicantShortlisted(private val binding:ListItemSubmitApplicationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: DataListApplicant, adapterListApplicantCallback: AdapterListApplicantCallback){
            with(binding){
                linearButton1.visibility = View.GONE
                linearButton2.visibility = View.VISIBLE
                tvNameApplicant.text = itemHaina.user?.fullname
                tvLastPosition.text = itemHaina.user?.workExperience?.position
                val dateStartExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val dateEndExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val totalExperience = (dateEndExperience?.toInt()?.minus(dateStartExperience?.toInt()!!))
                val companyAndExperience = "${itemHaina.user?.workExperience?.company}(${totalExperience} Year(s))"
                tvLastCompanyAndExperience.text = companyAndExperience
                if (itemHaina.user?.workExperience == null){
                    tvLastCompanyAndExperience.visibility = View.GONE
                    tvLastPosition.text = context.getString(R.string.no_work_experience)
                    tvExpectedSalary.visibility = View.GONE
                } else {
                    val expectedSalary = "${context.getString(R.string.last_salary)} : ${Helper.convertToFormatMoneyIDRFilter(itemHaina.user.workExperience.salary.toString())}"
                    tvExpectedSalary.text = expectedSalary
                }
                val lastEducation = "${context.getString(R.string.last_education)} : ${itemHaina.user?.education?.degreeName}-${itemHaina.user?.education?.major}"
                tvLastEducation.text = lastEducation
                btnInterviewShortlisted.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnInterview, adapterPosition, itemHaina, listApplicant)
                }
                btnReject.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnReject, adapterPosition, itemHaina, listApplicant)
                }
            }
        }
    }

    inner class ViewHolderInterview(private val binding:ListItemSubmitApplicationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: DataListApplicant, adapterListApplicantCallback: AdapterListApplicantCallback){
            with(binding){
                linearButton1.visibility = View.GONE
                linearButton2.visibility = View.GONE
                linearButton3.visibility = View.VISIBLE
                tvNameApplicant.text = itemHaina.user?.fullname
                tvLastPosition.text = itemHaina.user?.workExperience?.position
                val dateStartExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val dateEndExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val totalExperience = (dateEndExperience?.toInt()?.minus(dateStartExperience?.toInt()!!))
                val companyAndExperience = "${itemHaina.user?.workExperience?.company}(${totalExperience} Year(s))"
                tvLastCompanyAndExperience.text = companyAndExperience
                if (itemHaina.user?.workExperience == null){
                    tvLastCompanyAndExperience.visibility = View.GONE
                    tvLastPosition.text = context.getString(R.string.no_work_experience)
                    tvExpectedSalary.visibility = View.GONE
                } else {
                    val expectedSalary = "${context.getString(R.string.last_salary)} : ${Helper.convertToFormatMoneyIDRFilter(itemHaina.user.workExperience.salary.toString())}"
                    tvExpectedSalary.text = expectedSalary
                }
                val lastEducation = "${context.getString(R.string.last_education)} : ${itemHaina.user?.education?.degreeName}-${itemHaina.user?.education?.major}"
                tvLastEducation.text = lastEducation
                btnAccept.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnAccept, adapterPosition, itemHaina, listApplicant)
                }
                btnReject.setOnClickListener {
                    adapterListApplicantCallback.rejectApplicantClick(btnReject, adapterPosition, itemHaina, listApplicant)
                }
                cvInterview.visibility=View.VISIBLE
                tvInterview.text = itemHaina.interviewData?.method
                tvInterviewDate.text = itemHaina.interviewData?.time
                etInterviewContactPerson.text = itemHaina.interviewData?.cpName
                etInterviewContactNumber.text = itemHaina.interviewData?.cpPhone
                etInterviewLocation.text = itemHaina.interviewData?.location
            }
        }
    }

    inner class ViewHolderApplicantAccepted(private val binding:ListItemSubmitApplicationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemHaina: DataListApplicant, adapterListApplicantCallback: AdapterListApplicantCallback){
            with(binding){
                linearButton1.visibility = View.GONE
                linearButton2.visibility = View.GONE
                linearButton3.visibility = View.GONE
                tvNameApplicant.text = itemHaina.user?.fullname
                tvLastPosition.text = itemHaina.user?.workExperience?.position
                val dateStartExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val dateEndExperience = itemHaina.user?.workExperience?.dateStart?.substring(0, 4)
                val totalExperience = (dateEndExperience?.toInt()?.minus(dateStartExperience?.toInt()!!))
                val companyAndExperience = "${itemHaina.user?.workExperience?.company}(${totalExperience} Year(s))"
                tvLastCompanyAndExperience.text = companyAndExperience
                if (itemHaina.user?.workExperience == null){
                    tvLastCompanyAndExperience.visibility = View.GONE
                    tvLastPosition.text = context.getString(R.string.no_work_experience)
                    tvExpectedSalary.visibility = View.GONE
                } else {
                    val expectedSalary = "${context.getString(R.string.last_salary)} : ${Helper.convertToFormatMoneyIDRFilter(itemHaina.user.workExperience.salary.toString())}"
                    tvExpectedSalary.text = expectedSalary
                }
                val lastEducation = "${context.getString(R.string.last_education)} : ${itemHaina.user?.education?.degreeName}-${itemHaina.user?.education?.major}"
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
            2 -> {
                ViewHolderApplicantShortlisted(
                    ListItemSubmitApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            3 -> {
                ViewHolderInterview(
                    ListItemSubmitApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                ViewHolderApplicantAccepted(
                    ListItemSubmitApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val allApplicant: DataListApplicant? = listApplicant?.get(position)
        when(VIEW_TYPE){
            1-> {
                allApplicant?.let { (holder as ViewHolderAllApplicant).bind(it, adapterListApplicantCallback) }
            }
            2-> {
                allApplicant?.let { (holder as ViewHolderApplicantShortlisted).bind(it, adapterListApplicantCallback) }
            }
            3-> {
                allApplicant?.let { (holder as ViewHolderInterview).bind(it, adapterListApplicantCallback) }
            }
            else-> {
                allApplicant?.let { (holder as ViewHolderApplicantAccepted).bind(it, adapterListApplicantCallback) }
            }
        }
    }

    override fun getItemCount(): Int {
        return when(VIEW_TYPE){
            1->{
                listApplicant?.size?:0
            }
            2 -> {
                listApplicant?.size?:0
            }
            3 -> {
                listApplicant?.size?:0
            }
            else -> {
                listApplicant?.size?:0
            }
        }
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

  interface AdapterListApplicantCallback{
      fun rejectApplicantClick(view:View, adapterPosition:Int, data:DataListApplicant, listApplicant:ArrayList<DataListApplicant?>?)
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