package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemJobVacancyBinding
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import java.util.ArrayList

class AdapterAllVacancy(val context: Context,
                        private val listAllVacancy: ArrayList<DataAllVacancy?>?, private val adapterCallbackAllVacancy:AdapterCallbackAllVacancy, ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    inner class ViewHolderAllVacancy(val binding: ListItemJobVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataAllVacancy, adapterCallbackAllVacancy: AdapterCallbackAllVacancy) {
            with(binding) {
                tvTitleJob.text = itemHaina.position
                tvCompanyName.text = itemHaina.co
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return ViewHolderAllVacancy(
            ListItemJobVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level: DataAllVacancy? = listAllVacancy?.get(position)
        (holder as ViewHolderAllVacancy).bind(level!!, adapterCallbackAllVacancy)
    }

    override fun getItemCount(): Int {
        return listAllVacancy!!.size

    }

    interface AdapterCallbackAllVacancy {
        fun listAllVacancyClick(view: View, dataMyVacancy: DataAllVacancy, buttonState:Int?)
    }

    fun addAllVacancy(data: List<DataAllVacancy?>?) {
        data?.let { listAllVacancy?.addAll(it) }
        notifyItemRangeInserted((listAllVacancy?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listAllVacancy?.clear()
        notifyDataSetChanged()
    }

}