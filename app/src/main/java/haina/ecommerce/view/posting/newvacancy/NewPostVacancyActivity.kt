package haina.ecommerce.view.posting.newvacancy

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.ActivityPostNewVacancyBinding
import haina.ecommerce.helper.Helper.changeFormatMoneyToValue
import haina.ecommerce.helper.NumberTextWatcher
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.view.login.LoginActivity
import timber.log.Timber
import java.util.*

class NewPostVacancyActivity : AppCompatActivity(), VacancyContract, View.OnClickListener, AdapterDataCreateVacancy.AdapterCallbackFillDataVacancy {

    private lateinit var binding:ActivityPostNewVacancyBinding
    private lateinit var presenter: VacancyPresenter
    private var popupDialogType:Dialog? = null
    private var popupDialogLevel:Dialog? = null
    private var popupDialogExperience:Dialog? = null
    private var popupDialogSpecialist:Dialog? = null
    private var popupDialogLocation:Dialog? = null
    private var type:Int? = null
    private var idCompany:Int? = null
    private var level:Int? = null
    private var experience:Int? = null
    private var specialist:Int? = null
    private var location:Int? = null
    private var showSalary:Int = 0
    private lateinit var request : RequestCreateVacancy
    private lateinit var dataCreateVacancy :DataCreateVacancy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNewVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener(this)
        binding.etType.setOnClickListener(this)
        binding.etLevel.setOnClickListener(this)
        binding.etLocationJob.setOnClickListener(this)
        binding.etExperience.setOnClickListener(this)
        binding.etSpecialist.setOnClickListener(this)
        refresh()
        binding.toolbarCreateVacancy.setNavigationOnClickListener {
            onBackPressed()
        }
        dataCreateVacancy = intent.getParcelableExtra("dataCreateVacancy")!!
        val dataLocationJob = intent.getParcelableArrayListExtra<DataItemHaina>("locationJob")
        for (i in 1..10){
            popupDialogExperience(listOf(i))
        }
        popupDialogType(dataCreateVacancy.vacancyType)
        popupDialogLevel(dataCreateVacancy.vacancyLevel)
        popupDialogLocation(dataLocationJob)
        popupDialogSpecialist(dataCreateVacancy.vacancySpecialist)

        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val low: TextWatcher = NumberTextWatcher(binding.etMinimSalary, locale, numDecs)
        val high: TextWatcher = NumberTextWatcher(binding.etHighSalary, locale, numDecs)
        binding.etMinimSalary.addTextChangedListener(low)
        binding.etHighSalary.addTextChangedListener(high)

        binding.cbShowSalary.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                showSalary = 1
                Toast.makeText(applicationContext, "This salary will be show", Toast.LENGTH_SHORT).show()
            }
            else
                showSalary = 0
                Toast.makeText(applicationContext, "You hide the salary", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login_not_login -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_next -> {
                checkDataVacancyFirst()
            }
            R.id.et_type -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 3
                popupDialogType?.show()
            }
            R.id.et_level -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 1
                popupDialogLevel?.show()
            }
            R.id.et_location_job -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 2
                popupDialogLocation?.show()
            }
            R.id.et_experience -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 4
                popupDialogExperience?.show()
            }
            R.id.et_specialist -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 5
                popupDialogSpecialist?.show()
            }
        }
    }

    private val adapterType by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, arrayListOf(),
            null, null, null, null, null,
            this, null, null, null)
    }

    private val adapterLevel by lazy {
        AdapterDataCreateVacancy(applicationContext, arrayListOf(), null, null,
            null, null, null, null, null,
            this, null, null, null)
    }

    private val adapterLocation by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null,
            null, null, null, arrayListOf(), null,
            this, null, null, null)
    }

    private val adapterExperience by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null,
            null, null, arrayListOf(), null, null,
            this, null, null, null)
    }

    private val adapterSpecialist by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null,
            null, null, null, null, arrayListOf(),
            this, null, null, null)
    }

    private fun popupDialogType(data: List<VacancyTypeItem?>?) {
        adapterType.clear()
        adapterType.addVacancyType(data)
        popupDialogType = Dialog(this)
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
        popupDialogLevel = Dialog(this)
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
        popupDialogLocation = Dialog(this)
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
        title?.text = "Location"
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

    private fun popupDialogExperience(data: List<Int?>?) {
        adapterExperience.clear()
        adapterExperience.addVacancyExperience(data)
        popupDialogExperience = Dialog(this)
        popupDialogExperience?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogExperience?.setCancelable(true)
        popupDialogExperience?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogExperience?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogExperience?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogExperience?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogExperience?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogExperience?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogExperience?.dismiss() }
        title?.text = "Year Experience"
        searchView?.visibility = View.GONE
        rvDestination?.adapter = adapterExperience
    }

    private fun popupDialogSpecialist(data: List<VacancySpecialistItem?>?) {
        adapterSpecialist.clear()
        adapterSpecialist.addVacancySpecialist(data)
        popupDialogSpecialist = Dialog(this)
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

    private fun checkDataVacancyFirst(){
        val isEmptyPositionJob:Boolean
        val isEmptyType:Boolean
        val isEmptyLevel:Boolean
        val isEmptyExperience:Boolean
        val isEmptySpecialist:Boolean
        val isEmptyLocation:Boolean
        val isEmptyAddress:Boolean
        val isEmptyMinSalary:Boolean
        val isEmptyMaxSalary:Boolean
        val isEmptyDescription:Boolean

        var positionJob = binding.etPositionJob.text.toString()
        var typeParams = type
        var levelParams = level
        var experienceParams = experience
        var specialistParams = specialist
        var locationParams = location
        var address = binding.etAddress.text.toString()
        var minSalary = changeFormatMoneyToValue(binding.etMinimSalary.text.toString())
        var highSalary = changeFormatMoneyToValue(binding.etHighSalary.text.toString())
        var description = binding.etDescriptionAds.text.toString()

        if (positionJob.isNullOrEmpty()){
            binding.tvTitlePositionJob.error = getString(R.string.cant_empty)
            isEmptyPositionJob = true
        } else {
            binding.tvTitlePositionJob.error = null
            isEmptyPositionJob = false
            positionJob = binding.etPositionJob.text.toString()
        }

        if (typeParams == null){
            binding.tvTitleType.error = getString(R.string.cant_empty)
            isEmptyType = true
        } else {
            binding.tvTitleType.error = null
            isEmptyType = false
            typeParams = type
        }

        if (levelParams == null){
            binding.tvTitleLevel.error = getString(R.string.cant_empty)
            isEmptyLevel = true
        } else {
            binding.tvTitleLevel.error = null
            isEmptyLevel = false
            levelParams = level
        }

        if (experienceParams == null){
            binding.tvTitleExperience.error = getString(R.string.cant_empty)
            isEmptyExperience = true
        } else {
            binding.tvTitleExperience.error = null
            isEmptyExperience = false
            experienceParams = experience
        }

        if (specialistParams == null){
            binding.tvTitleSpecialist.error = getString(R.string.cant_empty)
            isEmptySpecialist = true
        } else {
            binding.tvTitleSpecialist.error = null
            isEmptySpecialist = false
            specialistParams = specialist
        }

        if (locationParams == null){
            binding.tvTitleLocation.error = getString(R.string.cant_empty)
            isEmptyLocation = true
        } else {
            binding.tvTitleLocation.error = null
            isEmptyLocation = false
            locationParams = location
        }

        if (minSalary.isNullOrEmpty()){
            binding.tvTitleSalary.error = getString(R.string.cant_empty)
            isEmptyMinSalary = true
        } else {
            binding.tvTitleSalary.error = null
            isEmptyMinSalary = false
            minSalary = changeFormatMoneyToValue(binding.etMinimSalary.text.toString())
        }

        if (highSalary.isNullOrEmpty()){
            binding.tvTitleSalary.error = getString(R.string.cant_empty)
            isEmptyMaxSalary = true
        } else {
            binding.tvTitleSalary.error = null
            isEmptyMaxSalary = false
            highSalary = changeFormatMoneyToValue(binding.etHighSalary.text.toString())
        }

        if (description.isNullOrEmpty()){
            binding.tvDescriptionJob.error = getString(R.string.cant_empty)
            isEmptyDescription = true
        } else {
            binding.tvDescriptionJob.error = null
            isEmptyDescription = false
            description = binding.etDescriptionAds.text.toString()
        }

        if (address.isNullOrEmpty()){
            binding.tvTitleAddress.error = getString(R.string.cant_empty)
            isEmptyAddress = true
        } else {
            binding.tvTitleAddress.error = null
            isEmptyAddress = false
            address = binding.etAddress.text.toString()
        }

        if (!isEmptyPositionJob && !isEmptyType && !isEmptyLevel && !isEmptyExperience && !isEmptySpecialist && !isEmptyLocation &&
            !isEmptyAddress && !isEmptyMinSalary && !isEmptyMaxSalary && !isEmptyDescription){
            request = RequestCreateVacancy(positionJob, 0, specialistParams!!, levelParams!!, typeParams!!, description,
                experienceParams!!, null, minSalary, highSalary,
                showSalary, address, locationParams!!, null, null, null)
            startActivity(Intent(applicationContext, SkillAndEducationActivity::class.java)
                .putExtra("dataRequest", request)
                .putExtra("dataCreateVacancy", dataCreateVacancy))
        } else {
            Toast.makeText(applicationContext, "Please complete the form", Toast.LENGTH_SHORT).show()
        }
    }

    override fun listLevelClick(view: View, dataVacancyLevel: VacancyLevelItem) {
        when(view.id){
            R.id.relative_click -> {
                binding.etLevel.setText(dataVacancyLevel.name)
                popupDialogLevel?.dismiss()
                level = dataVacancyLevel.id
            }
        }
    }

    override fun listTypeClick(view: View, data: VacancyTypeItem) {
        when(view.id){
            R.id.relative_click -> {
                binding.etType.setText(data.name)
                popupDialogType?.dismiss()
                type = data.id
            }
        }
    }

    override fun listLocationClick(view: View, data: DataItemHaina) {
        when(view.id){
            R.id.relative_click -> {
                binding.etLocationJob.setText(data.name)
                popupDialogLocation?.dismiss()
                location = data.id
            }
        }
    }

    override fun listExperienceClick(view: View, yearExperience: Int) {
        when(view.id){
            R.id.relative_click -> {
                binding.etExperience.setText(yearExperience.toString())
                popupDialogExperience?.dismiss()
                experience = yearExperience
            }
        }
    }

    override fun listSpecialistClick(view: View, data: VacancySpecialistItem) {
        when(view.id){
            R.id.relative_click -> {
                binding.etSpecialist.setText(data.name)
                popupDialogSpecialist?.dismiss()
                specialist = data.id
            }
        }
    }
}