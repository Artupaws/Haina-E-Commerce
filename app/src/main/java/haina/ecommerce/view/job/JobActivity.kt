package haina.ecommerce.view.job

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.slider.RangeSlider
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobCategoryOnJob
import haina.ecommerce.adapter.AdapterLocationFilterJob
import haina.ecommerce.adapter.vacancy.AdapterAllVacancy
import haina.ecommerce.databinding.ActivityJobBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.detailjob.DetailJobActivity
import haina.ecommerce.view.job.bookmark.JobBookmarkActivity
import haina.ecommerce.view.job.filter.BottomSheetFilterFragment
import haina.ecommerce.view.login.LoginActivity
import haina.ecommerce.view.posting.newvacancy.NewPostVacancyActivity
import haina.ecommerce.view.register.company.RegisterCompanyActivity
import timber.log.Timber
import java.util.*

class JobActivity : AppCompatActivity(), JobContract.View,
    View.OnClickListener, AdapterAllVacancy.AdapterCallbackAllVacancy{

    private lateinit var binding: ActivityJobBinding
    private lateinit var presenter: JobPresenter
    private var popupFilter: Dialog? = null
    private var progressDialog: Dialog? = null
    private var popupCheckDataCompany: Dialog? = null
    private val helper: Helper = Helper
    private var broadcaster: LocalBroadcastManager? = null
    val data:MutableMap<String, Int> = HashMap()
    var filterCategory:Int = 0
    var filterLocation:Int = 0
    private var listLocationFilter: List<DataItemHaina?>? = null
    private var filterStartSalary:Int = 0
    private var isCategoryEmpty = true
    private var isLocationEmpty = true
    private var isStartSalaryEmpty = true
    private var dataCreateVacancy:DataCreateVacancy? = null
    private lateinit var sharedPref:SharedPreferenceHelper
    private var idCompany:Int = 0

    private var minSalary:Int? = null
    private var idEdu: Int? = null
    private var idSpecialist: Int? = null
    private var idCity: Int? = null
    private var type: Int? = null
    private var level: Int? = null
    private var experience: Int? = null
    private var filterSheet: BottomSheetFilterFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = JobPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
        filterSheet= BottomSheetFilterFragment()
        presenter.loadAllVacancy(
            minSalary,
            idEdu,
            idSpecialist,
            idCity,
            type,
            level,
            experience
        )
        presenter.loadListJobLocation()
        presenter.getDataCreateVacancy()
        presenter.checkRegisterCompany()
        refresh()
        loadingDialog()
        showPopupRegisterCompany()
        if (!sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.searchView.visibility = View.GONE
            binding.fabCreateVacancy.visibility = View.GONE
            binding.rvJob.visibility = View.GONE
        } else{
            binding.includeLogin.linearNotLogin.visibility = View.GONE
            binding.searchView.visibility = View.VISIBLE
            binding.fabCreateVacancy.visibility = View.VISIBLE
            binding.rvJob.visibility = View.VISIBLE
        }
        binding.toolbarJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarJob.setNavigationOnClickListener { onBackPressed() }
        binding.cvFilterJob.setOnClickListener(this)
        binding.fabCreateVacancy.setOnClickListener(this)
//        binding.rvJob.adapter = adapterListJob
        binding.rvJob.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.fabCreateVacancy.visibility = View.GONE
                }
                if (dy < 0) {
                    binding.fabCreateVacancy.visibility = View.VISIBLE
                }
            }
        })
        binding.toolbarJob.inflateMenu(R.menu.menu_filter_job)
        binding.toolbarJob.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.filter_job -> {
                    showFilter()

//                    popupFilter?.show()
                    true
                }
                R.id.bookmark_job -> {
                    startActivity(Intent(applicationContext, JobBookmarkActivity::class.java))
                    true
                }
                else -> false
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty()!!){
                    (binding.rvJob.adapter as AdapterAllVacancy).filter.filter(newText)
                    (binding.rvJob.adapter as AdapterAllVacancy).notifyDataSetChanged()
                }
                return true
            }

        })
    }

    private fun showFilter(){

        filterSheet!!.show(supportFragmentManager, filterSheet!!.tag)

    }
    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.loadAllVacancy(
                minSalary,
                idEdu,
                idSpecialist,
                idCity,
                type,
                level,
                experience)
            presenter.loadListJobLocation()
            presenter.getDataCreateVacancy()
        })
    }

    private fun loadingDialog(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun loadPresenterFilter(){
        presenter.loadAllVacancy(
            minSalary,
            idEdu,
            idSpecialist,
            idCity,
            type,
            level,
            experience
        )
    }

    private fun showPopupRegisterCompany() {
        popupCheckDataCompany = Dialog(this)
        popupCheckDataCompany?.setContentView(R.layout.popup_check_register_company)
        popupCheckDataCompany?.setCancelable(true)
        popupCheckDataCompany?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, android.R.color.white))
        val window:Window = popupCheckDataCompany?.window!!
        window.setGravity(Gravity.CENTER)
        val actionCancel = popupCheckDataCompany?.findViewById<TextView>(R.id.tv_action_cancel)
        val actionYes = popupCheckDataCompany?.findViewById<TextView>(R.id.tv_action_yes)
        actionCancel?.setOnClickListener { popupCheckDataCompany?.dismiss() }
        actionYes?.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterCompanyActivity::class.java))
            popupCheckDataCompany?.dismiss()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.cv_filter_job -> {
                popupFilter?.show()
            }
            R.id.fab_create_vacancy -> {
                if (idCompany == 0) popupCheckDataCompany?.show()
                else startActivity(Intent(applicationContext, NewPostVacancyActivity::class.java)
                    .putExtra("dataCreateVacancy", dataCreateVacancy)
                    .putParcelableArrayListExtra("locationJob", listLocationFilter as ArrayList)
                    .putExtra("idCompany", idCompany))
            }
            R.id.btn_login_not_login -> {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.checkRegisterCompany()
        presenter.getDataCreateVacancy()
        presenter.loadListJobLocation()
//        presenter.loadListJobCategory()
    }

    override fun onStart() {
        super.onStart()
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("jobLocationFilter"))
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("jobCategoryFilter"))
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("jobFilter"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "jobLocationFilter" -> {
                    filterLocation = intent.getIntExtra("idLocationFilter", 0)
                }
                "jobCategoryFilter" -> {
                    filterCategory = intent.getIntExtra("idCategoryJobFilter", 0)
                    loadPresenterFilter()
                }
                "jobFilter" -> {
                    minSalary = if(intent.getIntExtra("minSalary",0)==0){
                        null
                    }else{
                        intent.getIntExtra("minSalary",0)
                    }
                    idEdu = if(intent.getIntExtra("idEdu",0)==0){
                        null
                    }else{
                        intent.getIntExtra("idEdu",0)
                    }
                    idSpecialist = if(intent.getIntExtra("idSpecialist",0)==0){
                        null
                    }else{
                        intent.getIntExtra("idSpecialist",0)
                    }
                    idCity = if(intent.getIntExtra("idCity",0)==0){
                        null
                    }else{
                        intent.getIntExtra("idCity",0)
                    }
                    type = if(intent.getIntExtra("type",0)==0){
                        null
                    }else{
                        intent.getIntExtra("type",0)
                    }
                    level = if(intent.getIntExtra("level",0)==0){
                        null
                    }else{
                        intent.getIntExtra("level",0)
                    }
                    experience = if(intent.getIntExtra("experience",0)==0){
                        null
                    }else{
                        intent.getIntExtra("experience",0)
                    }
                    loadPresenterFilter()

                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        broadcaster?.unregisterReceiver(mMessageReceiver)
    }

    override fun messageLoadListJob(msg: String) {
        Timber.d(msg)
        data.clear()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getLoadListJob(list: List<DataItemJob?>?) {
//        if (list !=null){
//            binding.includeEmpty.linearEmpty.visibility = View.GONE
//            adapterListJob.clear()
//            adapterListJob.add(list)
//        } else {
//            binding.rvJob.visibility = View.GONE
//            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
//        }
    }

    override fun getDataSize(list: Int?) {
        if (list == 0){
            Toast.makeText(applicationContext, list.toString(), Toast.LENGTH_SHORT).show()
            binding.rvJob.visibility = View.INVISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        } else {
            binding.rvJob.visibility = View.VISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
        }
    }

//    private val adapterListJob by lazy {
//        AdapterJobVacancy(applicationContext, arrayListOf(), this)
//    }

    private val adapterListAllVacancy by lazy {
        AdapterAllVacancy(applicationContext, arrayListOf(), this)
    }

    override fun messageLoadJobCategory(msg: String) {
        Timber.d(msg)
        binding.swipeRefresh.isRefreshing = false
//        presenter.loadListJobVacancy(data)
    }

    override fun getLoadJobCategory(itemHaina: MutableList<DataItemHaina?>?) {
        val category = mutableListOf<DataItemHaina?>()
        category.addAll(listOf(DataItemHaina("All Category", "All Category", "",-1)))
        category.addAll(itemHaina!!)
        val jobCategoryAdapter = AdapterJobCategoryOnJob(this, category)
    }

    override fun getLoadListLocation(itemHaina: List<DataItemHaina?>?) {
        listLocationFilter = itemHaina
        dialogFilterJob(listLocationFilter)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogFilterJob(itemHaina: List<DataItemHaina?>?){
        popupFilter = Dialog(this)
        popupFilter?.setContentView(R.layout.popup_filter_job)
        popupFilter?.setCancelable(true)
        popupFilter?.window?.setBackgroundDrawable(getDrawable(android.R.color.transparent))
        val window: Window = popupFilter?.window!!
        window.setGravity(Gravity.CENTER)
        val action = popupFilter?.findViewById<Button>(haina.ecommerce.R.id.btn_apply)
        val rvJobLocation = popupFilter?.findViewById<RecyclerView>(haina.ecommerce.R.id.rv_job_location)
        val relativeLoading = popupFilter?.findViewById<RelativeLayout>(R.id.relative_loading)
        val rSliderStartSalary = popupFilter?.findViewById<RangeSlider>(R.id.rslider_start_salary)
        val tvStartSalary = popupFilter?.findViewById<TextView>(R.id.tv_start_salary)
        val jobLocationAdapter = AdapterLocationFilterJob(this, itemHaina)
        rSliderStartSalary?.addOnChangeListener { _, value, _ ->
            action?.isEnabled = value.toString()!=""
            minSalary = value.toInt()
            tvStartSalary?.text = helper.convertToFormatMoneyIDRFilter(value.toString())
            filterStartSalary = helper.changeFormatMoneyToValueFilter(tvStartSalary?.text.toString())!!.toInt()
        }
        jobLocationAdapter.onItemClick = {
            action?.isEnabled = it.toString()!=""
        }
        action?.setOnClickListener{
            popupFilter!!.dismiss()

            loadPresenterFilter()
        }
        rvJobLocation?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = jobLocationAdapter
            jobLocationAdapter.notifyDataSetChanged()
        }
    }

    override fun messageLoadListLocation(msg: String) {
        Timber.d(msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getDataCreateVacancy(data: DataCreateVacancy?) {
        dataCreateVacancy = data
    }

    override fun messageGetDataCreateVacancy(msg: String) {
        Timber.d(msg)
    }

    override fun messageCheckRegisterCompany(msg: String) {
        Timber.d(msg)
    }

    override fun getDataRegisterCompany(data: DataCompany) {
        idCompany = data.id!!
    }

    override fun messageGetAllVacancy(msg: String) {
        Timber.d(msg)
    }

    override fun getDataAllVacancy(data: List<DataAllVacancy?>?) {
        Timber.d(data?.size.toString())
        adapterListAllVacancy.clear()
        adapterListAllVacancy.addAllVacancy(data)
        binding.rvJob.adapter = adapterListAllVacancy
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

//    override fun listJobClick(view: View, data: DataItemJob) {
//
//    }

    override fun listAllVacancyClick(view: View, dataMyVacancy: DataAllVacancy) {
        when(view.id){
            R.id.linear_job_vacancy -> {
                startActivity(Intent(applicationContext, DetailJobActivity::class.java).putExtra("detailJob", dataMyVacancy)
                    .putExtra("idCompany", idCompany))
            }
        }
    }

    override fun addBookmarkJob(idVacancy: Int) {
        presenter.addBookmark(idVacancy)
    }

    override fun removeBookmarkJob(idVacancy: Int) {
        presenter.removeBookmark(idVacancy)
    }
}


