package haina.ecommerce.view.posting.newvacancy

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.ActivityPackagePriceVacancyBinding
import haina.ecommerce.helper.Helper.changeFormatMoneyToValue
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.RequestCreateVacancy
import haina.ecommerce.model.vacancy.VacancyPackageItem
import haina.ecommerce.view.checkout.CheckoutActivity
import haina.ecommerce.view.property.FinishPropertyActivity
import timber.log.Timber

class PackagePriceVacancyActivity : AppCompatActivity(),
    View.OnClickListener, AdapterDataCreateVacancy.AdapterCallbackPackage,
VacancyContract.ViewCreateVacancyFree{

    private lateinit var binding:ActivityPackagePriceVacancyBinding
    private var request:RequestCreateVacancy? = null
    private lateinit var dataCreateVacancy: DataCreateVacancy
    private var idPackage:Int? = null
    private var progressDialog:Dialog? = null
    private lateinit var presenter: VacancyPresenter
    private var price:Int = 0
    private var packageNameAds:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackagePriceVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = VacancyPresenter(this, this)
        binding.toolbar12.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.button.setOnClickListener(this)
        request = intent.getParcelableExtra("dataRequest")
        dataCreateVacancy = intent.getParcelableExtra("dataCreateVacancy")!!
        AdapterDataCreateVacancy.VIEW_TYPE = 9
        adapterPackage.clear()
        adapterPackage.addVacancyPackageAds(dataCreateVacancy.vacancyPackage)
        binding.rvPackageVacancy.adapter= adapterPackage
        dialogLoading()
    }

    private val adapterPackage by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null, null, arrayListOf(),
            null, null, null, null, null, this, null)
    }

    private fun checkDataPackage(){
        val isEmptyIdPackage:Boolean
        var idPackageParams = idPackage
        if (idPackageParams == null){
            isEmptyIdPackage = true
            binding.tvWarningEmpty.visibility = View.VISIBLE
        } else {
            isEmptyIdPackage = false
            binding.tvWarningEmpty.visibility = View.GONE
            idPackageParams = idPackage
        }

        if (!isEmptyIdPackage){
            request = RequestCreateVacancy(this.request!!.position, this.request!!.idCompany, this.request!!.idSpecialist, this.request!!.level, this.request!!.type, this.request!!.description,
                this.request!!.experience, this.request!!.idEdu, this.request!!.minSalary, this.request!!.maxSalary, this.request!!.salaryDisplay,
                this.request!!.address, this.request!!.idCity, idPackageParams, null, this.request!!.skill)
            when(idPackageParams){
                1 -> {
                    Timber.d("packageOne")
                    presenter.createVacancyFree(request!!.position, request!!.idCompany, request!!.idSpecialist, request!!.level, request!!.type, request!!.description, request!!.experience, request!!.idEdu!!, request!!.minSalary.toInt(),
                        request!!.maxSalary.toInt(), request!!.salaryDisplay, request!!.address, request!!.idCity, request!!.packageAds!!, request!!.skill!!)
                }
                2 -> {
                    Timber.d("packageTwo")
                    startActivity(Intent(applicationContext, CheckoutActivity::class.java)
                        .putExtra("typeTransaction", 5)
                        .putExtra("dataRequest", request)
                        .putExtra("priceVacancy", price)
                        .putExtra("packageName", packageNameAds))
                }
                3 -> {
                    Timber.d("packageThree")
                    startActivity(Intent(applicationContext, CheckoutActivity::class.java)
                        .putExtra("typeTransaction", 5)
                        .putExtra("dataRequest", request)
                        .putExtra("priceVacancy", price)
                        .putExtra("packageName", packageNameAds))
                }
            }
        } else {
            binding.tvWarningEmpty.visibility = View.VISIBLE
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(true)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window:Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button -> {
                checkDataPackage()
            }
        }
    }

    override fun listPackageClick(view: View, data: VacancyPackageItem) {
        when(view.id){
            R.id.cv_click -> {
                binding.tvWarningEmpty.visibility = View.INVISIBLE
                idPackage = data.id
                price = changeFormatMoneyToValue(data.price.toString()).toInt()
                packageNameAds = data.name!!
                Timber.d(data.id.toString())
            }
        }
    }

    override fun messageCreateVacancy(msg: String) {
        Timber.d(msg)
        if (msg.contains("Free vacancy created successfully!"))
            startActivity(Intent(applicationContext, FinishPropertyActivity::class.java).putExtra("finish", "vacancy"))
        finishAffinity()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}