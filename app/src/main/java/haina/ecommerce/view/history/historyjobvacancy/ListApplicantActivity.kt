package haina.ecommerce.view.history.historyjobvacancy

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterListApplicant
import haina.ecommerce.databinding.ActivityListApplicantBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.DataListApplicant
import haina.ecommerce.model.vacancy.DataMyVacancy
import haina.ecommerce.view.posting.newvacancy.NewPostVacancyActivity
import timber.log.Timber
import java.sql.Array
import java.util.ArrayList

class ListApplicantActivity : AppCompatActivity(),
    MyVacancyContract.ViewListApplicant, View.OnClickListener, AdapterListApplicant.AdapterListApplicantCallback {

    private lateinit var binding:ActivityListApplicantBinding
    private var progressDialog: Dialog? = null
    private lateinit var presenter:ListApplicantPresenter
    private var dataVacancy:DataMyVacancy? = null
    private var dataCreateVacancy: DataCreateVacancy? = null
    private var listLocationFilter: List<DataItemHaina?>? = null
    private var listApplicant: ArrayList<DataListApplicant?>? = null
    private var adapterPositionParams:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ListApplicantPresenter(this, this)
        dialogLoading()
        binding.relativeClick.setOnClickListener(this)
        dataVacancy = intent.getParcelableExtra("dataVacancy")
        dataCreateVacancy = intent.getParcelableExtra("dataCreateVacancy")
        listLocationFilter = intent.getParcelableArrayListExtra("locationJob")
        dataVacancy.let { it?.id?.let { it1 -> presenter.getListApplicant(it1) } }
        setDataVacancy(dataVacancy)
        val title = intent?.getStringExtra("title")
        binding.toolbarListApplicant.title = title
        binding.toolbarListApplicant.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setDataVacancy(data:DataMyVacancy?){
        binding.tvTitlePositionJob.text = data?.position
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(applicationContext,
                R.color.white))
        val window : Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageGetListApplicant(msg: String) {
        Timber.d(msg)
    }

    override fun getDataListApplicant(data: List<DataListApplicant?>?) {
        AdapterListApplicant.VIEW_TYPE = 1
        adapterListApplicant.clear()
        listApplicant = data as ArrayList
        adapterListApplicant.addListApplicant(data)
        binding.rvListApplicant.adapter = adapterListApplicant
    }

    override fun messageUpdateApplicantStatus(msg: String) {
        Timber.d(msg)
        if (msg.contains("success")){
            listApplicant?.removeAt(adapterPositionParams)
            adapterListApplicant.notifyItemRemoved(adapterPositionParams)
        }
    }

    private val adapterListApplicant by lazy {
        AdapterListApplicant(applicationContext, arrayListOf(), this)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.relative_click -> {
                startActivity(
                    Intent(applicationContext, NewPostVacancyActivity::class.java)
                    .putExtra("detailVacancy", dataVacancy)
                    .putExtra("dataCreateVacancy", dataCreateVacancy)
                    .putParcelableArrayListExtra("locationJob", listLocationFilter as ArrayList))
            }
        }
    }

    override fun rejectApplicantClick(view: View, adapterPosition: Int, data: DataListApplicant, listApplicant: ArrayList<DataListApplicant?>?) {
        when(view.id){
            R.id.btn_reject -> {
                adapterPositionParams = adapterPosition
                presenter.rejectAppliocant(data.id!!, "not accepted")
            }
        }
    }
}