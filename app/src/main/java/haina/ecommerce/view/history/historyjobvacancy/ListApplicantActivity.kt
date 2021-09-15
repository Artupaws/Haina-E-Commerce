package haina.ecommerce.view.history.historyjobvacancy

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAddOn
import haina.ecommerce.adapter.vacancy.AdapterListApplicant
import haina.ecommerce.databinding.ActivityListApplicantBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.flight.MealInfosItem
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.DataListApplicant
import haina.ecommerce.model.vacancy.DataMyVacancy
import haina.ecommerce.view.posting.newvacancy.NewPostVacancyActivity
import timber.log.Timber
import java.sql.Array
import java.util.ArrayList

class ListApplicantActivity : AppCompatActivity(),
    MyVacancyContract.ViewListApplicant, View.OnClickListener, AdapterListApplicant.AdapterListApplicantCallback, AdapterView.OnItemSelectedListener {

    private lateinit var binding:ActivityListApplicantBinding
    private var progressDialog: Dialog? = null
    private lateinit var presenter:ListApplicantPresenter
    private var dataVacancy:DataMyVacancy? = null
    private var dataCreateVacancy: DataCreateVacancy? = null
    private var listLocationFilter: List<DataItemHaina?>? = null
    private var listApplicant: ArrayList<DataListApplicant?>? = null
    private var adapterPositionParams:Int = 0
    private var popupSetInterview:Dialog? = null
    private var clLocation:ConstraintLayout? = null
    private var clContactNumber:ConstraintLayout? = null
    private var clContactPerson:ConstraintLayout? = null
    private var etContactPerson:EditText? = null
    private var etLocation:EditText? = null
    private var etContactNumber:EditText? = null
    private var interviewMethod = arrayOf("phone", "live", "online")

    private var spinnerMethod:Spinner? = null

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
        val title = intent?.getStringExtra("title")
        binding.toolbarListApplicant.title = title
        binding.toolbarListApplicant.setNavigationOnClickListener { onBackPressed() }
        when(title){
            "Unprocess Applicant" -> {
                dataVacancy.let { it?.id?.let { it1 -> presenter.getListApplicant(it1) } }
                AdapterListApplicant.VIEW_TYPE = 1
            }
            "Choosed Applicant" -> {
                dataVacancy.let { it?.id?.let { it1 -> presenter.getListApplicantShortListed(it1) } }
                AdapterListApplicant.VIEW_TYPE = 2
            }
            "Interview Applicant" -> {
                dataVacancy.let { it?.id?.let { it1 -> presenter.getListApplicantInterview(it1) } }
                AdapterListApplicant.VIEW_TYPE = 3
            }
            "Accepted Applicant" -> {
                dataVacancy.let { it?.id?.let { it1 -> presenter.getListApplicantAccepted(it1) } }
                AdapterListApplicant.VIEW_TYPE = 4
            }
        }
        setDataVacancy(dataVacancy)

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
        adapterListApplicant.clear()
        listApplicant = data as ArrayList
        adapterListApplicant.addListApplicant(data)
        binding.rvListApplicant.adapter = adapterListApplicant
    }

    override fun messageUpdateApplicantStatus(msg: String) {
        Timber.d(msg)
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
                presenter.rejectAppliocant(data.id!!, "not accepted")
                listApplicant?.removeAt(adapterPosition)
                adapterListApplicant.notifyItemRemoved(adapterPosition)
            }
            R.id.btn_shortlisted -> {
                presenter.rejectAppliocant(data.id!!, "shortlisted")
                listApplicant?.removeAt(adapterPosition)
                adapterListApplicant.notifyItemRemoved(adapterPosition)
            }
            R.id.btn_accept -> {
                presenter.rejectAppliocant(data.id!!, "accepted")
                listApplicant?.removeAt(adapterPosition)
                adapterListApplicant.notifyItemRemoved(adapterPosition)
            }
            R.id.btn_interview -> {
                popupInterview(data,adapterPosition)
            }
        }
    }


    private fun popupInterview(dataApplicant: DataListApplicant, adapterPosition:Int) {
        Timber.d("interview")
        popupSetInterview = Dialog(this)
        popupSetInterview?.setContentView(R.layout.popup_invite_interview)
        popupSetInterview?.setCancelable(true)
        popupSetInterview?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupSetInterview?.window!!
        window.setGravity(Gravity.CENTER)

        val tvNameApplicant = popupSetInterview?.findViewById<TextView>(R.id.tv_name_applicant)
        val tvLastPosition = popupSetInterview?.findViewById<TextView>(R.id.tv_last_position)
        val tvLastCompany = popupSetInterview?.findViewById<TextView>(R.id.tv_last_company_and_experience)
        val tvExpectedSalary = popupSetInterview?.findViewById<TextView>(R.id.tv_expected_salary)
        val tvLastEducation = popupSetInterview?.findViewById<TextView>(R.id.tv_last_education)

        tvNameApplicant?.text=dataApplicant.user?.fullname

        tvLastPosition?.text = dataApplicant.user?.workExperience?.position
        val dateStartExperience = dataApplicant.user?.workExperience?.dateStart?.substring(0, 4)
        val dateEndExperience = dataApplicant.user?.workExperience?.dateStart?.substring(0, 4)
        val totalExperience = (dateEndExperience?.toInt()?.minus(dateStartExperience?.toInt()!!))
        val companyAndExperience = "${dataApplicant.user?.workExperience?.company}(${totalExperience} Year(s))"
        tvLastCompany?.text = companyAndExperience
        if (dataApplicant.user?.workExperience == null){
            tvLastCompany?.visibility = View.GONE
            tvLastPosition?.text = "No Work Experience"
            tvExpectedSalary?.visibility = View.GONE
        } else {
            val expectedSalary = "Last Salary : ${Helper.convertToFormatMoneyIDRFilter(dataApplicant.user.workExperience.salary.toString())}"
            tvExpectedSalary?.text = expectedSalary
        }
        val lastEducation = "Last Education : ${dataApplicant.user?.education?.degreeName}-${dataApplicant.user?.education?.major}"
        tvLastEducation?.text = lastEducation


        clLocation = popupSetInterview?.findViewById<ConstraintLayout>(R.id.cl_location)
        clContactPerson = popupSetInterview?.findViewById<ConstraintLayout>(R.id.cl_contact_person)
        clContactNumber = popupSetInterview?.findViewById<ConstraintLayout>(R.id.cl_contact_number)

        etLocation = popupSetInterview?.findViewById<EditText>(R.id.et_interview_location)
        etContactNumber = popupSetInterview?.findViewById<EditText>(R.id.et_interview_contact_number)
        etContactPerson= popupSetInterview?.findViewById<EditText>(R.id.et_interview_contact_person)

        spinnerMethod = popupSetInterview?.findViewById<Spinner>(R.id.spinner_interview)

        spinnerMethod?.adapter=ArrayAdapter(this, R.layout.list_item_invite_interview, interviewMethod)

        spinnerMethod?.onItemSelectedListener=this

        popupSetInterview?.show()
    }

    private fun checkInterviewData(applicantID:Int){
        var isLocationEmpty:Boolean = true
        var isContactPersonEmpty:Boolean = true
        var isContactNumberEmpty:Boolean = true
        var isDateEmpty:Boolean = true
        if(spinnerMethod?.selectedItem != "phone"){
            if(etLocation?.text.isNullOrEmpty()){
                isLocationEmpty=false
            }
        }
        if(etContactNumber?.text.isNullOrEmpty()){
            isContactNumberEmpty=false
        }

        if(etContactPerson?.text.isNullOrEmpty()){
            isContactPersonEmpty=false
        }

        if(isLocationEmpty && isContactNumberEmpty && isContactPersonEmpty && isDateEmpty){
            presenter.inviteInterview(applicantID)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        if(interviewMethod[position] =="phone"){
            clLocation!!.visibility=View.GONE
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}