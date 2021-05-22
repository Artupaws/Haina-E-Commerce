package haina.ecommerce.view.job

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.slider.RangeSlider
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobCategoryOnJob
import haina.ecommerce.adapter.AdapterJobVacancy
import haina.ecommerce.adapter.AdapterLocationFilterJob
import haina.ecommerce.databinding.ActivityJobBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob
import java.util.*

class JobActivity : AppCompatActivity(), JobContract, View.OnClickListener{

    private lateinit var binding: ActivityJobBinding
    private lateinit var presenter: JobPresenter
    private var popupFilter: Dialog? = null
    private var popupLoading: Dialog? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = JobPresenter(this)
        presenter.loadListJobCategory()
        presenter.loadListJobLocation()
        refresh()
        loadingDialog()
        binding.toolbarJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarJob.setNavigationOnClickListener { onBackPressed() }
        binding.cvFilterJob.setOnClickListener(this)

        binding.rvJob.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.cvFilterJob.visibility = View.GONE
                }
                if (dy < 0) {
                    binding.cvFilterJob.visibility = View.VISIBLE
                }
            }

        })
        popupLoading?.show()

    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.loadListJobCategory()
            presenter.loadListJobLocation()
        })
    }

    private fun loadingDialog(){
        popupLoading = Dialog(this)
        popupLoading?.setContentView(R.layout.layout_popup_dialog)
        popupLoading?.setCancelable(false)
        popupLoading?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window: Window = popupLoading?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun loadPresenterFilter(){
        popupLoading?.show()
        data.clear()
        isCategoryEmpty = filterCategory == 0
        isLocationEmpty = filterLocation == 0
        isStartSalaryEmpty = filterStartSalary == 0
        if (!isCategoryEmpty){
            data["id_category"] = filterCategory
        }
        if (!isLocationEmpty){
            data["id_location"] = filterLocation
        }
        if (!isStartSalaryEmpty){
            data["salary_start"] = filterStartSalary
        }
        presenter.loadListJobVacancy(data)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.cv_filter_job -> {
                popupFilter?.show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("jobLocationFilter"))
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("jobCategoryFilter"))
    }

    override fun onDestroy() {
        super.onDestroy()
        popupLoading?.dismiss()
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "jobLocationFilter" -> {
                    filterLocation = intent.getIntExtra("idLocationFilter", 0)
                }
                "jobCategoryFilter" -> {
                    filterCategory = intent.getIntExtra("idCategoryJobFilter", 0)
                    popupLoading?.show()
                    loadPresenterFilter()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        broadcaster?.unregisterReceiver(mMessageReceiver)
    }

    override fun successLoadListJob(msg: String) {
        Log.d("loadlistJobSuccess", msg)
        data.clear()
        binding.swipeRefresh.isRefreshing = false
        binding.ivLoadingVacancy.visibility = View.INVISIBLE
        popupLoading?.dismiss()
    }

    override fun errorLoadListJob(msg: String) {
        Log.d("loadlistJobError", msg)
        data.clear()
        binding.swipeRefresh.isRefreshing = false
        binding.ivLoadingVacancy.visibility = View.INVISIBLE
        binding.rvJob.visibility = View.INVISIBLE
        binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        popupLoading?.dismiss()
    }

    override fun getLoadListJob(list: List<DataItemJob?>?) {
        val listJobAdapter = AdapterJobVacancy(this, list)
        if (list!!.isNotEmpty()) {
            binding.rvJob.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = listJobAdapter
                listJobAdapter.notifyDataSetChanged()
                binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            }
        } else {
            binding.rvJob.visibility = View.INVISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        }
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

    override fun successLoadJobCategory(msg: String) {
        Log.d("loadJobCategorySuccess", msg)
        binding.swipeRefresh.isRefreshing = false
        binding.ivLoadingCategory.visibility = View.INVISIBLE
        presenter.loadListJobVacancy(data)
    }

    override fun errorLoadJobCategory(msg: String) {
        Log.d("loadJobCategoryError", msg)
        binding.swipeRefresh.isRefreshing = false
        binding.ivLoadingCategory.visibility = View.INVISIBLE
    }

    override fun getLoadJobCategory(itemHaina: MutableList<DataItemHaina?>?) {
        val category = mutableListOf<DataItemHaina?>()
        category.addAll(listOf(DataItemHaina("All Category", "All Category", "",-1)))
        category.addAll(itemHaina!!)
        val jobCategoryAdapter = AdapterJobCategoryOnJob(this, category)
        binding.rvCategoryJob.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = jobCategoryAdapter
            jobCategoryAdapter.notifyDataSetChanged()
        }
    }

    override fun getLoadListLocation(itemHaina: List<DataItemHaina?>?) {
        Log.d("listJob", itemHaina?.size.toString())
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
        rSliderStartSalary?.addOnChangeListener { slider, value, fromUser ->
            action?.isEnabled = value.toString()!=""
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

    override fun successLoadListLocation(msg: String) {
        Log.d("successLoadListLocation", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun errorLoadListLocation(msg: String) {
        Log.d("errorLoadListLocation", msg)
        binding.swipeRefresh.isRefreshing = false
    }
}


