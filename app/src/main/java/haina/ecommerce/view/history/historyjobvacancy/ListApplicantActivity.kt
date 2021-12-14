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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAddOn
import haina.ecommerce.adapter.vacancy.AdapterListApplicant
import haina.ecommerce.databinding.ActivityListApplicantBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.flight.MealInfosItem
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.DataListApplicant
import haina.ecommerce.model.vacancy.DataMyVacancy
import haina.ecommerce.model.vacancy.InterviewData
import haina.ecommerce.view.posting.newvacancy.NewPostVacancyActivity
import timber.log.Timber
import java.sql.Array
import java.util.*

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
    private var tvLocation:TextView? = null
    private var tvInterviewDate:TextView? = null
    private var etContactNumber:EditText? = null
    private var interviewMethod = arrayOf("phone", "live", "online")

    private var date: String = Calendar.getInstance().get(Calendar.DATE).toString()
    private var month: String = Calendar.getInstance().get(Calendar.MONTH).toString()

    private var datetime:String? = null
    private var duration:Int = 0

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
        tvInterviewDate = popupSetInterview?.findViewById<TextView>(R.id.tv_interview_date)

        tvInterviewDate?.setOnClickListener {
            setDateInterview()
        }


        clLocation = popupSetInterview?.findViewById<ConstraintLayout>(R.id.cl_location)
        clContactPerson = popupSetInterview?.findViewById<ConstraintLayout>(R.id.cl_contact_person)
        clContactNumber = popupSetInterview?.findViewById<ConstraintLayout>(R.id.cl_contact_number)

        etLocation = popupSetInterview?.findViewById<EditText>(R.id.et_interview_location)
        tvLocation = popupSetInterview?.findViewById<TextView>(R.id.tv_location)
        etContactNumber = popupSetInterview?.findViewById<EditText>(R.id.et_interview_contact_number)
        etContactPerson= popupSetInterview?.findViewById<EditText>(R.id.et_interview_contact_person)

        spinnerMethod = popupSetInterview?.findViewById<Spinner>(R.id.spinner_interview)

        spinnerMethod?.adapter=ArrayAdapter(this, R.layout.list_item_invite_interview, interviewMethod)

        spinnerMethod?.onItemSelectedListener=this

        val btnSave=popupSetInterview?.findViewById<Button>(R.id.btn_save)

        btnSave?.setOnClickListener {
            checkInterviewData(dataApplicant.id!!,adapterPosition)
        }

        popupSetInterview?.show()
    }

    private fun checkInterviewData(applicantID:Int,adapterPosition: Int){
        var isLocationEmpty = true
        var isContactPersonEmpty = true
        var isContactNumberEmpty = true
        var isDateEmpty = true

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
        if(tvInterviewDate?.text!!.equals("select date")){
            isDateEmpty=false
        }

        if(isLocationEmpty && isContactNumberEmpty && isContactPersonEmpty && isDateEmpty){
            presenter.inviteInterview(
                applicantID,
                spinnerMethod!!.selectedItem.toString(),
                datetime!!,
                duration,
                etLocation?.text.toString(),
                etContactPerson?.text.toString(),
                etContactNumber?.text.toString(),
                adapterPosition
            )
        }
    }

    private fun setDateInterview() {
        val datebuilder = MaterialDatePicker.Builder.datePicker()
        val now = Calendar.getInstance()
        datebuilder.setTitleText("Select Date Interview")
        datebuilder.setSelection(now.timeInMillis)
        datebuilder.setCalendarConstraints(limitRangeDate().build())

        val datepicker = datebuilder.build()
        datepicker.show(supportFragmentManager, datepicker.toString())
        datepicker.addOnNegativeButtonClickListener { datepicker.dismiss() }

        var date:String = Calendar.getInstance().get(Calendar.DATE).toString()
        var month:String = Calendar.getInstance().get(Calendar.MONTH).toString()
        var year:String = Calendar.getInstance().get(Calendar.YEAR).toString()
        var timefrom:String = ""
        var hourfrom:Int = 0
        var minutefrom:Int = 0

        var timeto:String = ""
        var hourto:Int = 0
        var minuteto:Int = 0
        var datefull:String = ""

        datepicker.addOnPositiveButtonClickListener {

            date = it?.convertLongtoTime("dd-MM-yyyy").toString().substring(0,2)
            month = it?.convertLongtoTime("dd-MM-yyyy").toString().substring(3,5)

            year = it?.convertLongtoTime("dd-MM-yyyy").toString().substring(6,10)

            datefull = it?.convertLongtoTime("yyyy-MM-dd").toString()

            val timebuilder = MaterialTimePicker.Builder()
            timebuilder.setTitleText("Select Time From")
            timebuilder.setHour(10)
            timebuilder.setMinute(0)
            timebuilder.setTimeFormat(TimeFormat.CLOCK_24H)

            val timepicker = timebuilder.build()
            timepicker.show(supportFragmentManager, timepicker.toString())
            timepicker.addOnNegativeButtonClickListener { timepicker.dismiss() }
            timepicker.addOnPositiveButtonClickListener {
                timefrom=timepicker.hour.toString()+":"+timepicker.minute.toString()+":00"
                hourfrom=timepicker.hour
                minutefrom=timepicker.minute

                val timetobuilder = MaterialTimePicker.Builder()
                timetobuilder.setTitleText("Select Time To")
                timetobuilder.setHour(10)
                timetobuilder.setMinute(0)
                timetobuilder.setTimeFormat(TimeFormat.CLOCK_24H)

                val timetopicker = timetobuilder.build()
                timetopicker.show(supportFragmentManager, timetopicker.toString())
                timetopicker.addOnNegativeButtonClickListener { timetopicker.dismiss() }
                timetopicker.addOnPositiveButtonClickListener {
                    timeto=timetopicker.hour.toString()+":"+timetopicker.minute.toString()+":00"
                    hourto=timetopicker.hour
                    minuteto=timetopicker.minute

                    duration=((hourto-hourfrom)*60) + (minuteto-minutefrom)
                    datetime="$datefull $timefrom"
                    Timber.d(duration.toString())

                    tvInterviewDate!!.text= "$datefull  $timefrom - $timeto"
                }

            }
        }
    }


    private fun limitRangeDate(): CalendarConstraints.Builder {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val calendarStart: Calendar = Calendar.getInstance()
        val calendarEnd: Calendar = Calendar.getInstance()
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val startMonth = month.toInt()
        val startDate = date.toInt()
        val endMonth = 12
        val endDate = 31

        calendarStart.set(year, startMonth, startDate-1)
        calendarEnd.set(year, endMonth - 1, endDate)

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
    }

    override fun inviteInterviewSuccess(data: InterviewData,adapterPosition: Int) {
        popupSetInterview?.dismiss()
        listApplicant?.removeAt(adapterPosition)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when {
            interviewMethod[position] =="phone" -> {
                clLocation!!.visibility=View.GONE
            }
            interviewMethod[position] =="live" -> {
                clLocation!!.visibility=View.VISIBLE
                tvLocation!!.text=resources.getString(R.string.interview_location)
                etLocation!!.hint=resources.getString(R.string.enter_interview_location)
            }
            interviewMethod[position] =="online" -> {
                clLocation!!.visibility=View.VISIBLE
                tvLocation!!.text=getString(R.string.interview_link)
                etLocation!!.hint=getString(R.string.enter_interview_link)

            }
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}