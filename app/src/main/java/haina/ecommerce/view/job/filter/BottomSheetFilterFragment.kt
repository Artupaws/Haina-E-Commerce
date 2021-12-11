package haina.ecommerce.view.job.filter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.FragmentJobFilterBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.view.hotels.selection.BottomSheetHotelFragment
import timber.log.Timber

class BottomSheetFilterFragment : BottomSheetDialogFragment(), BottomSheetFilterContract.View,
    AdapterDataCreateVacancy.AdapterCallbackFillDataVacancy,
    AdapterDataCreateVacancy.AdapterCallbackSkillEdu {

    private lateinit var _binding: FragmentJobFilterBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var presenter: BottomSheetFilterPresenter

    private var popupDialogType: Dialog? = null
    private var popupDialogLevel: Dialog? = null
    private var popupDialogSpecialist: Dialog? = null
    private var popupDialogLocation: Dialog? = null
    private var popupDialogLastEdu: Dialog? = null

    private var minSalary:Int? = null
    private var idEdu:Int? = null
    private var idSpecialist:Int? = null
    private var idCity:Int? = null
    private var type:Int? = null
    private var level:Int? = null
    private var experience:Int? = null

    private val helper: Helper = Helper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobFilterBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        presenter = BottomSheetFilterPresenter(this, requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getDataCreateVacancy()
        presenter.loadListJobLocation()

        binding.toolbarHotelSelection.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHotelSelection.setNavigationOnClickListener { dismiss() }
        binding.toolbarHotelSelection.title = getString(R.string.vacancies_filter)
        binding.rsliderStartSalary.addOnChangeListener { _, value, _ ->
            minSalary = value.toInt()
            val stringSalary= helper.convertToFormatMoneyIDRFilter(value.toString())
            if(value.toInt()>=10000000){
                binding.tvStartSalary.text = "$stringSalary+"
            }else{
                binding.tvStartSalary.text = "$stringSalary"
            }
        }



        binding.rsliderExperience.addOnChangeListener { _, value, _ ->
            experience = value.toInt()
            if(value.toInt()>=10){
                binding.tvExperience.text = "${value.toInt()}+ Years Experience(s)"
            }else{
                binding.tvExperience.text = "${value.toInt()} Years Experience(s)"
            }
        }

        binding.btnApply.setOnClickListener{
            passFilterData()
        }

        binding.tvLocation.setOnClickListener {
            AdapterDataCreateVacancy.VIEW_TYPE = 2
            popupDialogLocation!!.show()
        }
        binding.tvLevel.setOnClickListener {
            AdapterDataCreateVacancy.VIEW_TYPE = 1
            popupDialogLevel!!.show()
        }
        binding.tvSpecialist.setOnClickListener {
            AdapterDataCreateVacancy.VIEW_TYPE = 5
            popupDialogSpecialist!!.show()
        }
        binding.tvType.setOnClickListener {
            AdapterDataCreateVacancy.VIEW_TYPE = 3
            popupDialogType!!.show()
        }
        binding.tvEducation.setOnClickListener {
            AdapterDataCreateVacancy.VIEW_TYPE = 7
            popupDialogLastEdu!!.show()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetHotelFragment {
            val fragment = BottomSheetHotelFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    private fun passFilterData(){

        val intentDataFilter =Intent("jobFilter")
            .putExtra("minSalary", minSalary)
            .putExtra("idEdu",idEdu)
            .putExtra("idSpecialist",idSpecialist)
            .putExtra("idCity",idCity)
            .putExtra("type",type)
            .putExtra("level",level)
            .putExtra("experience",experience)
        broadcaster?.sendBroadcast(intentDataFilter)
        dismiss()

    }

    private val adapterType by lazy {
        AdapterDataCreateVacancy(requireContext(), null, null, arrayListOf(),
            null, null, null, null, null,
            this, null, null, null)
    }

    private val adapterLevel by lazy {
        AdapterDataCreateVacancy(requireContext(), arrayListOf(), null, null,
            null, null, null, null, null,
            this, null, null, null)
    }

    private val adapterLocation by lazy {
        AdapterDataCreateVacancy(requireContext(), null, null, null,
            null, null, null, arrayListOf(), null,
            this, null, null, null)
    }

    private val adapterSpecialist by lazy {
        AdapterDataCreateVacancy(requireContext(), null, null, null,
            null, null, null, null, arrayListOf(),
            this, null, null, null)
    }

    private val adapterLastEdu by lazy {
        AdapterDataCreateVacancy(requireContext(), null, arrayListOf(), null, null, null,
            null, null, null, null, this, null, null)
    }

    private fun popupDialogType(data: List<VacancyTypeItem?>?) {
        adapterType.clear()
        adapterType.addVacancyType(data)
        popupDialogType = Dialog(requireContext())
        popupDialogType?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogType?.setCancelable(true)
        popupDialogType?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogType?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogType?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogType?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogType?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogType?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogType?.dismiss() }
        title?.text = "Type"
        searchView?.visibility = View.GONE
        rvDestination?.adapter = adapterType
    }

    private fun popupDialogLevel(data: List<VacancyLevelItem?>?) {
        adapterLevel.clear()
        adapterLevel.addVacancyLevel(data)
        popupDialogLevel = Dialog(requireContext())
        popupDialogLevel?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogLevel?.setCancelable(true)
        popupDialogLevel?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogLevel?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogLevel?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogLevel?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogLevel?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogLevel?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogLevel?.dismiss() }
        title?.text = "Level"
        searchView?.visibility = View.GONE
        rvDestination?.adapter = adapterLevel
    }

    private fun popupDialogLocation(data: List<DataItemHaina?>?) {
        adapterLocation.clear()
        adapterLocation.addVacancyLocation(data)
        popupDialogLocation = Dialog(requireContext())
        popupDialogLocation?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogLocation?.setCancelable(true)
        popupDialogLocation?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogLocation?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogLocation?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogLocation?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogLocation?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogLocation?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogLocation?.dismiss() }
        title?.text = resources.getString(R.string.location)
        searchView?.queryHint = "Search City Here"
        rvDestination?.adapter = adapterLocation
        searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.d(newText)
                if (newText?.isEmpty()!!){
                    (rvDestination?.adapter as AdapterDataCreateVacancy).filter.filter(newText)
                    (rvDestination?.adapter as AdapterDataCreateVacancy).notifyDataSetChanged()
                } else {
                    (rvDestination?.adapter as AdapterDataCreateVacancy).filter.filter("")
                }
                return true
            }

        })
    }


    private fun popupDialogSpecialist(data: List<VacancySpecialistItem?>?) {
        adapterSpecialist.clear()
        adapterSpecialist.addVacancySpecialist(data)
        popupDialogSpecialist = Dialog(requireContext())
        popupDialogSpecialist?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogSpecialist?.setCancelable(true)
        popupDialogSpecialist?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogSpecialist?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogSpecialist?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogSpecialist?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogSpecialist?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogSpecialist?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogSpecialist?.dismiss() }
        title?.text = "Specialist"
        searchView?.queryHint = "Search Specialist Here"
        rvDestination?.adapter = adapterSpecialist
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty()!!){
                    (rvDestination?.adapter as AdapterDataCreateVacancy).filter.filter(newText)
                    (rvDestination?.adapter as AdapterDataCreateVacancy).notifyDataSetChanged()
                }
                return true
            }
        })
    }

    private fun popupDialogLastEducation(data: List<VacancyEducationItem?>?) {
        adapterLastEdu.clear()
        adapterLastEdu.addVacancyEdu(data)
        popupDialogLastEdu = Dialog(requireContext())
        popupDialogLastEdu?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogLastEdu?.setCancelable(true)
        popupDialogLastEdu?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogLastEdu?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogLastEdu?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogLastEdu?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogLastEdu?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogLastEdu?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogLastEdu?.dismiss() }
        title?.text = "Last Education"
        searchView?.visibility = View.GONE
        rvDestination?.adapter = adapterLastEdu
    }

    override fun messageGetData(msg: String) {
    }


    override fun getVacancyData(data: DataCreateVacancy?) {
        popupDialogLevel(data!!.vacancyLevel)
        popupDialogSpecialist(data.vacancySpecialist)
        popupDialogType(data.vacancyType)
        popupDialogLastEducation(data.vacancyEducation)
    }

    override fun getLoadListLocation(data: List<DataItemHaina?>?) {
        popupDialogLocation(data)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun listLevelClick(view: View, dataVacancyLevel: VacancyLevelItem) {
        level=dataVacancyLevel.id
        popupDialogLevel!!.dismiss()
        binding.tvLevel.text = dataVacancyLevel.name
    }

    override fun listTypeClick(view: View, data: VacancyTypeItem) {
        type=data.id
        popupDialogType!!.dismiss()
        binding.tvType.text = data.name
    }

    override fun listLocationClick(view: View, data: DataItemHaina) {
        idCity=data.id
        popupDialogLocation!!.dismiss()
        binding.tvLocation.text = data.name
    }

    override fun listExperienceClick(view: View, yearExperience: Int) {

    }

    override fun listSpecialistClick(view: View, data: VacancySpecialistItem) {
        idSpecialist=data.id
        popupDialogSpecialist!!.dismiss()
        binding.tvSpecialist.text = data.name
    }

    override fun listSkillsClick(view: View, data: VacancySkillItem) {
    }

    override fun listEduClick(view: View, data: VacancyEducationItem) {
        idEdu = data.id
        popupDialogLastEdu!!.dismiss()
        binding.tvEducation.text = data.name
    }

    override fun listChipsSkillClick(view: View, data: VacancySkillItem) {
        TODO("Not yet implemented")
    }
}