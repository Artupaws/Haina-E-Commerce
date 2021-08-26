package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFacilitiesRoomBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
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
    val itemAdapterCallback: ItemAdapterCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private var listResultLocation: ArrayList<DataItemHaina?>? = listVacancyLocation
    companion object {
        var VIEW_TYPE = 1
    }

    inner class ViewHolderVacancyLevel(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyLevelItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listLevelClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderType(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancyTypeItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listTypeClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancyLocation(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataItemHaina, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listLocationClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancyExperience(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: Int, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.toString()
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listExperienceClick(relativeClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderVacancySpecialist(val binding: ListItemFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: VacancySpecialistItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameFacilities.text = itemHaina.name
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listSpecialistClick(relativeClick, itemHaina)
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
            else -> {
                ViewHolderVacancySpecialist(
                    ListItemFacilitiesRoomBinding.inflate(
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
        when (VIEW_TYPE) {
            1 -> {
                (holder as ViewHolderVacancyLevel).bind(level!!, itemAdapterCallback)
            }
            2 -> {
                (holder as ViewHolderVacancyLocation).bind(location!!, itemAdapterCallback)
            }
            3 -> {
                (holder as ViewHolderType).bind(type!!, itemAdapterCallback)
            }
            4 -> {
                (holder as ViewHolderVacancyExperience).bind(yearExperience!!, itemAdapterCallback)
            }
            else -> {
                (holder as ViewHolderVacancySpecialist).bind(specialist!!, itemAdapterCallback)
            }
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return when (VIEW_TYPE) {
            1 -> {
                listVacancyLevel!!.size
            }
            2 -> {
                listVacancyLocation!!.size
            }
            3 -> {
                listVacancyType!!.size
            }
            4 -> {
                listExperience!!.size
            }
            else -> {
                listVacancySpecialist!!.size
            }
        }
    }

    interface ItemAdapterCallback {
        fun listLevelClick(view: View, data: VacancyLevelItem)
        fun listTypeClick(view: View, data: VacancyTypeItem)
        fun listLocationClick(view: View, data: DataItemHaina)
        fun listExperienceClick(view: View, yearExperience: Int)
        fun listSpecialistClick(view: View, data: VacancySpecialistItem)
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
            else -> {
                listVacancySpecialist?.clear()
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