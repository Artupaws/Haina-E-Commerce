package haina.ecommerce.view.job.bookmark

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterAllVacancy
import haina.ecommerce.adapter.vacancy.AdapterJobBookmark
import haina.ecommerce.databinding.ActivityJobBinding
import haina.ecommerce.databinding.ActivityJobBookmarkBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.detailjob.DetailJobActivity
import haina.ecommerce.view.job.JobContract
import haina.ecommerce.view.job.JobPresenter
import haina.ecommerce.view.login.LoginActivity
import java.util.HashMap

class JobBookmarkActivity: AppCompatActivity(), JobBookmarkContract.View,
    View.OnClickListener, AdapterJobBookmark.AdapterCallbackAllVacancy{

    private lateinit var binding: ActivityJobBookmarkBinding
    private lateinit var presenter: JobBookmarkPresenter
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
    private var dataCreateVacancy: DataCreateVacancy? = null
    private lateinit var sharedPref: SharedPreferenceHelper
    private var idCompany:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = JobBookmarkPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
        refresh()
        loadingDialog()

        if (!sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.rvJobBookmark.visibility = View.GONE
        } else{
            binding.includeLogin.linearNotLogin.visibility = View.GONE
            binding.rvJobBookmark.visibility = View.VISIBLE
            presenter.loadListBookmark()
        }
        binding.toolbarJobBookmark.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarJobBookmark.setNavigationOnClickListener { onBackPressed() }
        binding.cvFilterJob.setOnClickListener(this)
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.loadListBookmark()
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

    override fun messageLoadListBookmark(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun getLoadListBookmark(list: List<DataAllVacancy?>?) {
        binding.swipeRefresh.setRefreshing(false)

        adapterListAllVacancy.clear()
        adapterListAllVacancy.addAllVacancy(list)
        binding.rvJobBookmark.adapter = adapterListAllVacancy
    }

    override fun notAvailableBookmark() {
        binding.rvJobBookmark.visibility = View.INVISIBLE
        binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        binding.includeEmpty.tvEmpty.text="You don't have any bookmarked job"
    }


    override fun messageChangeBookmark(value: Int) {
        if(value==1){
            presenter.loadListBookmark()
        }
    }

    override fun showLoading() {
        progressDialog?.show()

    }

    override fun dismissLoading() {
        progressDialog?.dismiss()

    }

    private val adapterListAllVacancy by lazy {
        AdapterJobBookmark(applicationContext, arrayListOf(), this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.cv_filter_job -> {
                popupFilter?.show()
            }
            R.id.btn_login_not_login -> {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        }
    }

    override fun listAllVacancyClick(view: View, dataMyVacancy: DataAllVacancy) {
        when(view.id){
            R.id.linear_job_vacancy -> {
                startActivity(Intent(applicationContext, DetailJobActivity::class.java).putExtra("detailJob", dataMyVacancy)
                    .putExtra("idCompany", idCompany))
            }
        }
    }


    override fun removeBookmarkJob(idVacancy: Int) {
        presenter.removeBookmark(idVacancy)
    }


}