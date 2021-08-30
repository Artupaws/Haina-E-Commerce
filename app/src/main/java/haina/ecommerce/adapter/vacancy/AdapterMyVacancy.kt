package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemChooseChipsBinding
import haina.ecommerce.databinding.ListItemFacilitiesRoomBinding
import haina.ecommerce.databinding.ListItemPackageVacancyBinding
import haina.ecommerce.helper.Helper.convertToFormatMoneyIDRFilter
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import timber.log.Timber
import java.util.ArrayList

class AdapterMyVacancy(val context: Context,
    private val listVacancyLevel: ArrayList<VacancyLevelItem?>?, private val adapterCallbackMyVacancy:AdapterCallbackMyVacancy, ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    inner class ViewHolderVacancyLevel(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyLevelItem, adapterCallbackMyVacancy: AdapterCallbackMyVacancy) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    adapterCallbackMyVacancy.listLevelClick(relativeClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return ViewHolderVacancyLevel(
            ListItemFacilitiesRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level: VacancyLevelItem? = listVacancyLevel?.get(position)
        (holder as ViewHolderVacancyLevel).bind(level!!, adapterCallbackMyVacancy)
    }

    override fun getItemCount(): Int {
        return listVacancyLevel!!.size

    }

    interface AdapterCallbackMyVacancy {
        fun listLevelClick(view: View, dataVacancyLevel: VacancyLevelItem)
    }

    fun addVacancyLevel(data: List<VacancyLevelItem?>?) {
        data?.let { listVacancyLevel?.addAll(it) }
        notifyItemRangeInserted((listVacancyLevel?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listVacancyLevel?.clear()
        notifyDataSetChanged()
    }

}