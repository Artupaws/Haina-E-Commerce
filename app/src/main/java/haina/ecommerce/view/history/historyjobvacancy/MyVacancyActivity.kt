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
import haina.ecommerce.adapter.vacancy.AdapterMyVacancy
import haina.ecommerce.databinding.ActivityMyVacancyBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.DataMyVacancy
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import haina.ecommerce.view.posting.newvacancy.NewPostVacancyActivity
import timber.log.Timber
import java.util.ArrayList

class MyVacancyActivity : AppCompatActivity(), MyVacancyContract.View, AdapterMyVacancy.AdapterCallbackMyVacancy {

    private lateinit var binding:ActivityMyVacancyBinding
    private var progressDialog: Dialog? = null
    private lateinit var presenter: MyVacancyPresenter
    private lateinit var dataCreateVacancy:DataCreateVacancy
    private var listLocationFilter: List<DataItemHaina?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MyVacancyPresenter(this, this)
        binding.toolbarHistory.setNavigationOnClickListener { onBackPressed() }
        dialogLoading()
        presenter.getListMyVacancy()
        presenter.getDataCreateVacancy()
        presenter.loadListJobLocation()
        binding.rvMyVacancy.adapter = adapterMyVacancy
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,R.color.white))
        val window : Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private val adapterMyVacancy by lazy {
        AdapterMyVacancy(applicationContext, arrayListOf(), this)
    }

    override fun messageGetListMyVacancy(msg: String) {
        Timber.d(msg)
    }

    override fun getListMyVacancy(data: List<DataMyVacancy?>?) {
        adapterMyVacancy.clear()
        adapterMyVacancy.addVacancyLevel(data)
    }

    override fun getDataCreateVacancy(data: DataCreateVacancy?) {
        dataCreateVacancy = data!!
        Timber.d(dataCreateVacancy.toString())
    }

    override fun messageGetDataCreateVacancy(msg: String) {
        Timber.d(msg)
    }

    override fun getLoadListLocation(itemHaina: List<DataItemHaina?>?) {
        listLocationFilter = itemHaina
    }

    override fun messageLoadListLocation(msg: String) {
        Timber.d(msg)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listMyVacancy(view: View, dataMyVacancy: DataMyVacancy, buttonState:Int?) {
        when(view.id){
            R.id.relative_click -> {
                startActivity(Intent(applicationContext, NewPostVacancyActivity::class.java)
                    .putExtra("detailVacancy", dataMyVacancy)
                    .putExtra("dataCreateVacancy", dataCreateVacancy)
                    .putParcelableArrayListExtra("locationJob", listLocationFilter as ArrayList))
            }
            R.id.btn_show_again -> {
                when(buttonState){
                    1 -> {

                    }
                    2 -> {
                        startActivity(Intent(applicationContext, HistoryTransactionActivity::class.java))
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finishAffinity()
    }
}