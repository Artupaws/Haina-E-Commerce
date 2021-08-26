package haina.ecommerce.view.posting.newvacancy

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobPosting
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.ActivityPostNewVacancyBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity
import timber.log.Timber
import java.sql.Time
import java.util.ArrayList

class NewPostVacancyActivity : AppCompatActivity(), VacancyContract, View.OnClickListener, AdapterDataCreateVacancy.ItemAdapterCallback {

    private lateinit var binding:ActivityPostNewVacancyBinding
    private lateinit var presenter: VacancyPresenter
    private var popupDialogType:Dialog? = null
    private var popupDialogLevel:Dialog? = null
    private var popupDialogExperience:Dialog? = null
    private var popupDialogSpecialist:Dialog? = null
    private var popupDialogLocation:Dialog? = null

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
        val dataCreateVacancy = intent.getParcelableExtra<DataCreateVacancy>("dataCreateVacancy")
        val dataLocationJob = intent.getParcelableArrayListExtra<DataItemHaina>("locationJob")
        for (i in 1..10){
            popupDialogExperience(listOf(i))
        }
        popupDialogType(dataCreateVacancy?.vacancyType)
        popupDialogLevel(dataCreateVacancy?.vacancyLevel)
        popupDialogLocation(dataLocationJob)
        popupDialogSpecialist(dataCreateVacancy?.vacancySpecialist)
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
                startActivity(Intent(applicationContext, SkillAndEducationActivity::class.java))
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

    override fun successLoadMyPost(msg: String) {
        Log.d("MyPost", msg)
        if (msg.isEmpty()) {
//            binding.rvJobVacancy.visibility = View.INVISIBLE
//            binding.includeEmpty.tvEmpty.text = "You haven't posted anything yet"
//            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
//            binding.rvJobVacancy.visibility = View.VISIBLE
//            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun errorLoadMyPost(msg: String) {
        Log.d("errorLoadPost", msg)
    }

    override fun getListMyPost(list: List<DataMyJob?>?) {
    }

    private val adapterType by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, arrayListOf(),
            null, null, null, null, null, this)
    }

    private val adapterLevel by lazy {
        AdapterDataCreateVacancy(applicationContext, arrayListOf(), null, null,
            null, null, null, null, null,this)
    }

    private val adapterLocation by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null,
            null, null, null, arrayListOf(), null,this)
    }

    private val adapterExperience by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null,
            null, null, arrayListOf(), null, null,this)
    }

    private val adapterSpecialist by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null,
            null, null, null, null, arrayListOf(),this)
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

    override fun listLevelClick(view: View, data: VacancyLevelItem) {
        when(view.id){
            R.id.relative_click -> {
                binding.etLevel.setText(data.name)
                popupDialogLevel?.dismiss()
            }
        }
    }

    override fun listTypeClick(view: View, data: VacancyTypeItem) {
        when(view.id){
            R.id.relative_click -> {
                binding.etType.setText(data.name)
                popupDialogType?.dismiss()
            }
        }
    }

    override fun listLocationClick(view: View, data: DataItemHaina) {
        when(view.id){
            R.id.relative_click -> {
                binding.etLocationJob.setText(data.name)
                popupDialogLocation?.dismiss()
            }
        }
    }

    override fun listExperienceClick(view: View, yearExperience: Int) {
        when(view.id){
            R.id.relative_click -> {
                binding.etExperience.setText(yearExperience.toString())
                popupDialogExperience?.dismiss()
            }
        }
    }

    override fun listSpecialistClick(view: View, data: VacancySpecialistItem) {
        when(view.id){
            R.id.relative_click -> {
                binding.etSpecialist.setText(data.name)
                popupDialogSpecialist?.dismiss()
            }
        }
    }
}