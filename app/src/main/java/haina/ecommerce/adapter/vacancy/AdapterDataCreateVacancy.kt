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
import haina.ecommerce.util.Constants
import timber.log.Timber
import java.util.ArrayList

class AdapterDataCreateVacancy(
    val context: Context,
    private val listVacancyLevel: ArrayList<VacancyLevelItem?>?,
    private val listVacancyEducation: ArrayList<VacancyEducationItem?>?,
    private val listVacancyType: ArrayList<VacancyTypeItem?>?,
    private val listVacancySkill: ArrayList<VacancySkillItem?>?,
    private val listVacancyPackage: ArrayList<VacancyPackageItem?>?,
    private val listExperience: ArrayList<Int?>?,
    private var listVacancyLocation: ArrayList<DataItemHaina?>?,
    private val listVacancySpecialist: ArrayList<VacancySpecialistItem?>?,
    private val dataVacancyCallbackFillDataVacancyFirst: AdapterCallbackFillDataVacancy?,
    private val dataVacancyCallbackSecond: AdapterCallbackSkillEdu?,
    private val dataVacancyCallbackPackage: AdapterCallbackPackage?,
    private val listVacancySkillChoosed: ArrayList<VacancySkillItem?>?
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var index:Int = -1
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private var listResultLocation: ArrayList<DataItemHaina?>? = listVacancyLocation
    companion object {
        var VIEW_TYPE = 1
    }

    inner class ViewHolderVacancyLevel(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyLevelItem, adapterCallbackFillDataVacancy: AdapterCallbackFillDataVacancy) {
            with(binding) {
                if(sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en") {
                    tvNameFacilities.text = itemHaina.name
                }
                else{
                    tvNameFacilities.text = itemHaina.nameZh
                }
                relativeClick.setOnClickListener {
                    adapterCallbackFillDataVacancy.listLevelClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderType(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyTypeItem, adapterCallbackFillDataVacancy: AdapterCallbackFillDataVacancy) {
            with(binding) {
                if(sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en") {
                    tvNameFacilities.text = itemHaina.name
                }
                else{
                    tvNameFacilities.text = itemHaina.nameZh
                }
                relativeClick.setOnClickListener {
                    adapterCallbackFillDataVacancy.listTypeClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancyLocation(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataItemHaina, adapterCallbackFillDataVacancy: AdapterCallbackFillDataVacancy) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    adapterCallbackFillDataVacancy.listLocationClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancyExperience(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: Int, adapterCallbackFillDataVacancy: AdapterCallbackFillDataVacancy) {
            with(binding) {
                tvNameFacilities.text = itemHaina.toString()
                relativeClick.setOnClickListener {
                    adapterCallbackFillDataVacancy.listExperienceClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancySpecialist(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancySpecialistItem, adapterCallbackFillDataVacancy: AdapterCallbackFillDataVacancy) {
            with(binding) {
                if(sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en") {
                    tvNameFacilities.text = itemHaina.name
                }
                else{
                    tvNameFacilities.text = itemHaina.nameZh
                }
                relativeClick.setOnClickListener {
                    adapterCallbackFillDataVacancy.listSpecialistClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancySkills(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancySkillItem, dataVacancyCallbackSecond:AdapterCallbackSkillEdu?) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    dataVacancyCallbackSecond?.listSkillsClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderLastEducation(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyEducationItem, dataVacancyCallbackSecond:AdapterCallbackSkillEdu?) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    dataVacancyCallbackSecond?.listEduClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderSkillChoosed(val binding: ListItemChooseChipsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancySkillItem, dataVacancyCallbackSecond:AdapterCallbackSkillEdu?) {
            with(binding) {
                chip.text = itemHaina.name
                chip.setOnCloseIconClickListener {
                    dataVacancyCallbackSecond?.listChipsSkillClick(chip,itemHaina)
                }
            }
        }
    }

    inner class ViewHolderPackage(val binding: ListItemPackageVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyPackageItem, dataVacancyCallbackPackage:AdapterCallbackPackage?) {
            with(binding) {
               tvNamePackage.text = itemHaina.name
                tvPricePackage.text = convertToFormatMoneyIDRFilter(itemHaina.price.toString())
                if(sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en"){
                    tvDescriptionPackage.text = itemHaina.description
                }
                else{
                    tvDescriptionPackage.text = itemHaina.descriptionZh
                }
                cvClick.setOnClickListener { index = adapterPosition
                    notifyDataSetChanged()
                    dataVacancyCallbackPackage?.listPackageClick(cvClick, itemHaina)
                }
                if (index == adapterPosition){
                    relativeBackground.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                }
                else{
                    relativeBackground.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return when (VIEW_TYPE) {
            1 -> {
                ViewHolderVacancyLevel(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            2 -> {
                ViewHolderVacancyLocation(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            3 -> {
                ViewHolderType(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            4 -> {
                ViewHolderVacancyExperience(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            5 -> {
                ViewHolderVacancySpecialist(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            6 -> {
                ViewHolderVacancySkills(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            7 -> {
                ViewHolderLastEducation(
                    ListItemFacilitiesRoomBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            8 -> {
                ViewHolderSkillChoosed(
                    ListItemChooseChipsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                ViewHolderPackage(
                    ListItemPackageVacancyBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level: VacancyLevelItem? = listVacancyLevel?.get(position)
        val type: VacancyTypeItem? = listVacancyType?.get(position)
        val location: DataItemHaina? = listVacancyLocation?.get(position)
        val yearExperience: Int? = listExperience?.get(position)
        val specialist: VacancySpecialistItem? = listVacancySpecialist?.get(position)
        val skills: VacancySkillItem? = listVacancySkill?.get(position)
        val skillChoosed: VacancySkillItem? = listVacancySkillChoosed?.get(position)
        val lastEdu: VacancyEducationItem? = listVacancyEducation?.get(position)
        val packageAds: VacancyPackageItem? = listVacancyPackage?.get(position)
        when (VIEW_TYPE) {
            1 -> {
                (holder as ViewHolderVacancyLevel).bind(level!!, dataVacancyCallbackFillDataVacancyFirst!!)
            }
            2 -> {
                (holder as ViewHolderVacancyLocation).bind(location!!, dataVacancyCallbackFillDataVacancyFirst!!)
            }
            3 -> {
                (holder as ViewHolderType).bind(type!!, dataVacancyCallbackFillDataVacancyFirst!!)
            }
            4 -> {
                (holder as ViewHolderVacancyExperience).bind(yearExperience!!, dataVacancyCallbackFillDataVacancyFirst!!)
            }
            5 -> {
                (holder as ViewHolderVacancySpecialist).bind(specialist!!, dataVacancyCallbackFillDataVacancyFirst!!)
            }
            6 -> {
                (holder as ViewHolderVacancySkills).bind(skills!!, dataVacancyCallbackSecond)
            }
            7 -> {
                (holder as ViewHolderLastEducation).bind(lastEdu!!, dataVacancyCallbackSecond)
            }
            8 -> {
                (holder as ViewHolderSkillChoosed).bind(skillChoosed!!, dataVacancyCallbackSecond)
            }
            else -> {
                (holder as ViewHolderPackage).bind(packageAds!!, dataVacancyCallbackPackage)
            }
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return when (VIEW_TYPE) {
            1 -> {
                listVacancyLevel?.size?:0
            }
            2 -> {
                listVacancyLocation?.size?:0
            }
            3 -> {
                listVacancyType?.size?:0
            }
            4 -> {
                listExperience?.size?:0
            }
            5 -> {
                listVacancySpecialist?.size?:0
            }
            6 -> {
                listVacancySkill?.size?:0
            }
            7 -> {
                listVacancyEducation?.size?:0
            }
            8 -> {
                listVacancySkillChoosed?.size?:0
            }
            else -> {
                listVacancyPackage?.size?:0
            }
        }
    }

    interface AdapterCallbackFillDataVacancy {
        fun listLevelClick(view: View, dataVacancyLevel: VacancyLevelItem)
        fun listTypeClick(view: View, data: VacancyTypeItem)
        fun listLocationClick(view: View, data: DataItemHaina)
        fun listExperienceClick(view: View, yearExperience: Int)
        fun listSpecialistClick(view: View, data: VacancySpecialistItem)
    }

    interface AdapterCallbackSkillEdu {
        fun listSkillsClick(view: View, data: VacancySkillItem)
        fun listEduClick(view: View, data: VacancyEducationItem)
        fun listChipsSkillClick(view: View, data: VacancySkillItem)
    }

    interface AdapterCallbackPackage{
        fun listPackageClick(view: View, data:VacancyPackageItem)
    }

    fun addVacancyLevel(data: List<VacancyLevelItem?>?) {
        data?.let { listVacancyLevel?.addAll(it) }
        notifyItemRangeInserted((listVacancyLevel?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancyType(data: List<VacancyTypeItem?>?) {
        data?.let { listVacancyType?.addAll(it) }
        notifyItemRangeInserted((listVacancyType?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancyLocation(data: List<DataItemHaina?>?) {
        data?.let { listVacancyLocation?.addAll(it) }
        notifyItemRangeInserted((listVacancyLocation?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancyExperience(data: List<Int?>?) {
        listExperience?.addAll(data!!)
        notifyItemRangeInserted((listExperience?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancySpecialist(data: List<VacancySpecialistItem?>?) {
        data?.let { listVacancySpecialist?.addAll(it) }
        notifyItemRangeInserted((listVacancySpecialist?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancySkills(data: List<VacancySkillItem?>?) {
        data?.let { listVacancySkill?.addAll(it) }
        notifyItemRangeInserted((listVacancySkill?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancyEdu(data: List<VacancyEducationItem?>?) {
        data?.let { listVacancyEducation?.addAll(it) }
        notifyItemRangeInserted((listVacancyEducation?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancySkillChoosed(data: List<VacancySkillItem?>?) {
        data?.let { listVacancySkillChoosed?.addAll(it) }
        notifyItemRangeInserted((listVacancySkillChoosed?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun addVacancyPackageAds(data: List<VacancyPackageItem?>?) {
        data?.let { listVacancyPackage?.addAll(it) }
        notifyItemRangeInserted((listVacancyPackage?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        when(VIEW_TYPE) {
            1 -> {
                listVacancyLevel?.clear()
                notifyDataSetChanged()
            }
            2 -> {
                listVacancyLocation?.clear()
                notifyDataSetChanged()
            }
            3 -> {
                listVacancyType?.clear()
                notifyDataSetChanged()
            }
            4 -> {
                listVacancyLocation?.clear()
                notifyDataSetChanged()
            }
            5 -> {
                listVacancySpecialist?.clear()
                notifyDataSetChanged()
            }
            6 -> {
                listVacancySkill?.clear()
                notifyDataSetChanged()
            }
            7 -> {
                listVacancyEducation?.clear()
                notifyDataSetChanged()
            }
            8 -> {
                listVacancySkillChoosed?.clear()
                notifyDataSetChanged()
            }
            else -> {
                listVacancyPackage?.clear()
                notifyDataSetChanged()
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.lowercase()
                Timber.d(querySearch)
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null) {
                    listResultLocation
                } else {
                    listResultLocation?.filter {
                        it?.name?.lowercase()!!.contains(querySearch, true)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listVacancyLocation = p1?.values as ArrayList<DataItemHaina?>
                Timber.d(listVacancyLocation?.size.toString())
                notifyDataSetChanged()
            }

        }
    }

}