package haina.ecommerce.view.history.historyjobvacancy

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityMyVacancyBinding
import haina.ecommerce.model.vacancy.DataMyVacancy
import timber.log.Timber

class MyVacancyActivity : AppCompatActivity(), MyVacancyContract.View {

    private lateinit var binding:ActivityMyVacancyBinding
    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarHistory.setNavigationOnClickListener { onBackPressed() }
        dialogLoading()
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,R.color.white))
        val window : Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageGetListMyVacancy(msg: String) {
        Timber.d(msg)
    }

    override fun getListMyVacancy(data: List<DataMyVacancy?>?) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}