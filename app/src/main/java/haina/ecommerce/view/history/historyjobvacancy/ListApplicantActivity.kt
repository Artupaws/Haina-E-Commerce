package haina.ecommerce.view.history.historyjobvacancy

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterListApplicant
import haina.ecommerce.databinding.ActivityListApplicantBinding
import haina.ecommerce.model.vacancy.DataListApplicant
import timber.log.Timber

class ListApplicantActivity : AppCompatActivity(), MyVacancyContract.ViewListApplicant {

    private lateinit var binding:ActivityListApplicantBinding
    private var progressDialog: Dialog? = null
    private lateinit var presenter:ListApplicantPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ListApplicantPresenter(this, this)
        dialogLoading()
        val idVacancy = intent?.getIntExtra("idVacancy",0)
        idVacancy?.let { presenter.getListApplicant(it) }
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
        adapterListApplicant.addListApplicant(data)
        binding.rvListApplicant.adapter = adapterListApplicant
    }

    private val adapterListApplicant by lazy {
        AdapterListApplicant(applicationContext, arrayListOf())
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}