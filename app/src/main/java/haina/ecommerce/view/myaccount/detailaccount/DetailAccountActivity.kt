package haina.ecommerce.view.myaccount.detailaccount

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterDocumentUser
import haina.ecommerce.adapter.AdapterSkillsUser
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.ActivityDetailAccountBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.changeFormatMoneyToValue
import haina.ecommerce.helper.Helper.changeFormatMoneyToValueFilter
import haina.ecommerce.helper.Helper.convertToFormatMoneyIDRFilter
import haina.ecommerce.helper.Helper.dateFormatWorkExperience
import haina.ecommerce.helper.NumberTextWatcher
import haina.ecommerce.model.*
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.VacancyEducationItem
import haina.ecommerce.model.vacancy.VacancySkillItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.view.myaccount.addrequirement.AddRequirementActivity
import haina.ecommerce.view.myaccount.addskills.AddSkillsActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DetailAccountActivity : AppCompatActivity(), View.OnClickListener,
    DetailAccountContract.View, AdapterDataCreateVacancy.AdapterCallbackSkillEdu {

    private lateinit var binding: ActivityDetailAccountBinding
    private lateinit var presenter: DetailAccountPresenter
    lateinit var sharedPref: SharedPreferenceHelper
    private var broadcaster: LocalBroadcastManager? = null
    private var dateSetListener: OnDateSetListener? = null
    private var isEmptyFullname = true
    private var isEmptyBirthdate = true
    private var isEmptyGender = true
    private var isEmptyAddress = true
    private var isEmptyAbout = true
    private var gender:String? = null
    private var genderRadio:String? = null
    private var fullname:String? = null
    private var email:String? = null
    private var phone:String? = null
    private var address:String? = null
    private var birthdate:String? = null
    private var about:String? = null
    private var lastEducation:String? = null
    private var username:String? = null
    private var progressDialog:Dialog? = null
    private var idEdu:Int = 0
    var refresh:Int = 0
    private var dataWorkExperience: LatestWork? = null
    private var stateSetDate:String = ""
    private var popupAddEducation:Dialog? = null
    private var popupDialogLastEdu:Dialog? = null
    private var etLastEdu:EditText? = null
    private var linearGpa:LinearLayout? = null
    private var etGpa:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = DetailAccountPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        presenter.getDataUserProfile()
        presenter.getSkillsUser()
        presenter.getDataCreateVacancy()
        radioGrroup()
        setTextBirthDate()
        refresh()
        loadAllDocument()
        dialogLoading()

        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val salary: TextWatcher = NumberTextWatcher(binding.etSalary, locale, numDecs)
        binding.etSalary.addTextChangedListener(salary)

        binding.toolbarDetailAccount.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailAccount.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailAccount.title = "Detail Account"
        binding.ivActionEditPersonalData.setOnClickListener(this)
        binding.ivActionSavePersonalData.setOnClickListener(this)
        binding.etBirthdate.setOnClickListener(this)
        binding.tvAddResume.setOnClickListener(this)
        binding.tvAddPorto.setOnClickListener(this)
        binding.tvAddCertificate.setOnClickListener(this)
        binding.tvAddSkills.setOnClickListener(this)
        binding.etStart.setOnClickListener(this)
        binding.etEnd.setOnClickListener(this)
        binding.etLastEducation.setOnClickListener(this)
        binding.ivActionAddWorkExperience.setOnClickListener(this)
        binding.ivActionSaveWorkExperience.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("deleteSkill"))
    }

    override fun onResume() {
        super.onResume()
        presenter.getSkillsUser()
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action){
                "refreshSkill"->{
                    val fromIntent = intent.getIntExtra("addSkill", 0)
                    refresh = fromIntent
                    if (refresh == 1){
                        presenter.getSkillsUser()
                    }
                }
                "deleteSkill" -> {
                    val deleteSkill = intent.getStringExtra("nameSkill")
                    presenter.deleteSkills(deleteSkill!!)
                }
            }
        }

    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            loadAllDocument()
            presenter.getDataUserProfile()
            presenter.getSkillsUser()
        })
    }

    private fun loadAllDocument(){
        presenter.loadDocumentResume(1)
        presenter.loadDocumentPortfolio(2)
        presenter.loadDocumentCertificate(3)
    }

    private fun dialogAddEducation(dataEducation: EducationDetail?){
        popupAddEducation = Dialog(this)
        popupAddEducation?.setContentView(R.layout.popup_add_education)
        popupAddEducation?.setCancelable(true)
        popupAddEducation?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, android.R.color.white))
        val window:Window = popupAddEducation?.window!!
        window.setGravity(Gravity.CENTER)
        AdapterDataCreateVacancy.VIEW_TYPE = 7
//        val etDegree = popupAddEducation?.findViewById<EditText>(R.id.et_degree)
        etLastEdu = popupAddEducation?.findViewById<EditText>(R.id.et_degree)
        etGpa = popupAddEducation?.findViewById<EditText>(R.id.et_gpa)
        linearGpa = popupAddEducation?.findViewById<LinearLayout>(R.id.linear_gpa)
        etLastEdu?.setOnClickListener {
            popupDialogLastEdu?.show()
        }
        val buttonSave = popupAddEducation?.findViewById<Button>(R.id.btn_save_education)
        val etInstitution = popupAddEducation?.findViewById<EditText>(R.id.et_institution)
        val etStartYear = popupAddEducation?.findViewById<EditText>(R.id.et_year_start)
        val etEndYear = popupAddEducation?.findViewById<EditText>(R.id.et_year_end)
        val etMajor = popupAddEducation?.findViewById<EditText>(R.id.et_major)
        val etCity = popupAddEducation?.findViewById<EditText>(R.id.et_city)

        if (dataEducation != null){
            etInstitution?.setText(dataEducation.institution)
            etStartYear?.setText(dataEducation.yearStart)
            etEndYear?.setText(dataEducation.yearEnd)
            if (dataEducation.idEdu == 1) linearGpa?.visibility = View.GONE else linearGpa?.visibility = View.VISIBLE
            etGpa?.setText(dataEducation.gpa)
            etMajor?.setText(dataEducation.major)
            etLastEdu?.setText(dataEducation.degreeName)
            etCity?.setText(dataEducation.city)
            buttonSave?.text = "update"
        }

        buttonSave?.setOnClickListener {
            if (dataEducation != null){
                if (etInstitution?.text.toString().isNotEmpty() && etStartYear?.text.toString().isNotEmpty() && etEndYear?.text.toString().isNotEmpty() && etGpa?.text.toString().isNotEmpty()
                    && etMajor?.text.toString().isNotEmpty() && etCity?.text.toString().isNotEmpty() && etLastEdu?.text.toString().isNotEmpty()){
                        when(idEdu){
                            1-> {
                                presenter.updateLastEducation(etInstitution?.text.toString(), etStartYear?.text.toString(), etEndYear?.text.toString(),
                                    0.0, etMajor?.text.toString(), idEdu, etCity?.text.toString())
                            }
                            else -> {
                                presenter.updateLastEducation(etInstitution?.text.toString(), etStartYear?.text.toString(), etEndYear?.text.toString(),
                                    etGpa?.text.toString().toDouble(), etMajor?.text.toString(), idEdu, etCity?.text.toString())
                            }
                        }
                } else {
                    Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (etInstitution?.text.toString().isNotEmpty() && etStartYear?.text.toString().isNotEmpty() && etEndYear?.text.toString().isNotEmpty() && etGpa?.text.toString().isNotEmpty()
                    && etMajor?.text.toString().isNotEmpty() && etCity?.text.toString().isNotEmpty() && etLastEdu?.text.toString().isNotEmpty()){
                    when(idEdu){
                        1-> {
                            presenter.addLastEducation(etInstitution?.text.toString(), etStartYear?.text.toString(), etEndYear?.text.toString(),
                                0.0, etMajor?.text.toString(), idEdu, etCity?.text.toString())
                        }
                        else -> {
                            presenter.addLastEducation(etInstitution?.text.toString(), etStartYear?.text.toString(), etEndYear?.text.toString(),
                                etGpa?.text.toString().toDouble(), etMajor?.text.toString(), idEdu, etCity?.text.toString())
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun popupDialogLastEducation(data: List<VacancyEducationItem?>?) {
        adapterLastEdu.clear()
        adapterLastEdu.addVacancyEdu(data)
        popupDialogLastEdu = Dialog(this)
        popupDialogLastEdu?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogLastEdu?.setCancelable(true)
        popupDialogLastEdu?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogLastEdu?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogLastEdu?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogLastEdu?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogLastEdu?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogLastEdu?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogLastEdu?.dismiss() }
        title?.text = "Last Education"
        searchView?.visibility = View.GONE
        rvDestination?.adapter = adapterLastEdu
    }

    private fun setDataProfile(data: DataUser?) {
        fullname = data?.fullname
        email = data?.email
        username = data?.username
        phone = data?.phone
        address = data?.address
        birthdate = data?.birthdate
        gender = data?.gender
        about = data?.about
        lastEducation = "${data?.lastEducation}-${data?.educationDetail?.major}"
        Glide.with(this).load(data?.photo)
            .skipMemoryCache(false).diskCacheStrategy(
                DiskCacheStrategy.NONE
            ).listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    binding.progressCircular.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    binding.progressCircular.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    return false
                }

            }).into(binding.ivProfile)
        binding.tvFullname.text = fullname
        binding.etFullname.setText(binding.tvFullname.text)
        binding.tvEmail.text = email
        binding.etEmail.setText(binding.tvEmail.text)
        binding.tvUsername.text = username
        binding.etUsername.setText(binding.tvUsername.text)
        binding.tvPhone.text = phone
        binding.etPhone.setText(binding.tvPhone.text)
        binding.tvAddress.text = address
        binding.etAddress.setText(binding.tvAddress.text)
        binding.tvBirthdate.text = birthdate
        binding.etBirthdate.setText(binding.tvBirthdate.text)
        binding.tvGender.text = gender
        when {
            binding.tvGender.text.contains("Male") -> {
                binding.rbMale.isChecked = true
            }
            binding.tvGender.text.contains("Female") -> {
                binding.rbFemale.isChecked = true
            }
            else -> {
                binding.rbFemale.isChecked = false
                binding.rbMale.isChecked = false
            }
        }
        binding.tvAbout.text = about
        binding.etAbout.setText(binding.tvAbout.text)
        binding.tvLastEducation.text = lastEducation
        binding.etLastEducation.setText(lastEducation)
        binding.tvCompanyName.text = data?.latestWork?.company
        binding.etCompanyName.setText(data?.latestWork?.company)
        binding.tvPosition.text = data?.latestWork?.position
        binding.etPosition.setText(data?.latestWork?.position)
        binding.tvCityCompany.text = data?.latestWork?.city
        binding.etCityCompany.setText(data?.latestWork?.city)
        binding.tvStart.text = dateFormatWorkExperience(data?.latestWork?.dateStart)
        binding.etStart.setText(data?.latestWork?.dateStart)
        binding.tvEnd.text = dateFormatWorkExperience(data?.latestWork?.dateEnd)
        binding.etEnd.setText(data?.latestWork?.dateEnd)
        if (data?.latestWork?.salary != null){
            binding.tvSalary.text = convertToFormatMoneyIDRFilter(data.latestWork.salary.toString())
            binding.etSalary.setText(data.latestWork.salary.toString())
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_action_edit_personal_data -> {
                stateEdit()
            }
            R.id.iv_action_save_personal_data -> {
                stateCancelSave()
            }
            R.id.iv_action_add_work_experience -> {
                stateEditWorkExperience()
            }
            R.id.iv_action_save_work_experience -> {
                stateCancelSaveWorkExperience()
            }
            R.id.et_birthdate -> {
                stateSetDate = "birthdate"
                setDatePicker()
            }
            R.id.tv_add_resume ->{
                val intent = Intent(this, AddRequirementActivity::class.java)
                intent.putExtra("title", "Add Resume")
                startActivity(intent)
            }
            R.id.tv_add_porto ->{
                val intent = Intent(this, AddRequirementActivity::class.java)
                intent.putExtra("title", "Add Portfolio")
                startActivity(intent)
            }
            R.id.tv_add_certificate ->{
                val intent = Intent(this, AddRequirementActivity::class.java)
                intent.putExtra("title", "Add Certificate")
                startActivity(intent)
            }
            R.id.tv_add_skills ->{
                val intent = Intent(this, AddSkillsActivity::class.java)
                startActivity(intent)
            }
            R.id.et_start -> {
                stateSetDate = "startwork"
                setDatePicker()
            }
            R.id.et_end -> {
                stateSetDate = "endwork"
                setDatePicker()
            }
            R.id.et_last_education -> {
                popupAddEducation?.show()
            }
        }
    }

    private fun setDatePicker(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
            year, month, day)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun setTextBirthDate(){
        dateSetListener = OnDateSetListener { _, year, month, day ->
                val date = "$year-$month-$day"
            when(stateSetDate){
                "birthdate" -> {
                    binding.etBirthdate.setText(date)
                }
                "startwork" -> {
                    binding.etStart.setText(date)
                }
                "endwork" -> {
                    binding.etEnd.setText(date)
                }
                }
            }
    }

    private fun radioGrroup(){
        binding.rdGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(i)
                radioButton(radio)
            }
        )
    }

    private fun radioButton(view: View){
        val radio: RadioButton = findViewById(binding.rdGroup.checkedRadioButtonId)
        genderRadio = radio.text.toString()
        if(genderRadio!!.isNotEmpty()){
            binding.rbMale.error = null
            binding.rbFemale.error = null
        }
    }

    private fun stateEdit() {
        binding.ivActionEditPersonalData.visibility = View.INVISIBLE
        binding.ivActionSavePersonalData.visibility = View.VISIBLE
        binding.etFullname.visibility = View.VISIBLE
        binding.tvFullname.visibility = View.INVISIBLE
        binding.etEmail.visibility = View.VISIBLE
        binding.tvEmail.visibility = View.INVISIBLE
        binding.etUsername.visibility = View.VISIBLE
        binding.tvUsername.visibility = View.INVISIBLE
        binding.etPhone.visibility = View.VISIBLE
        binding.tvPhone.visibility = View.INVISIBLE
        binding.etAddress.visibility = View.VISIBLE
        binding.tvAddress.visibility = View.INVISIBLE
        binding.etBirthdate.visibility = View.VISIBLE
        binding.tvBirthdate.visibility = View.INVISIBLE
        binding.rdGroup.visibility = View.VISIBLE
        binding.tvGender.visibility = View.INVISIBLE
        binding.etAbout.visibility = View.VISIBLE
        binding.tvAbout.visibility = View.INVISIBLE
        binding.tvLastEducation.visibility = View.INVISIBLE
        binding.etLastEducation.visibility = View.VISIBLE
    }

    private fun stateEditWorkExperience(){
        binding.ivActionAddWorkExperience.visibility = View.INVISIBLE
        binding.ivActionSaveWorkExperience.visibility = View.VISIBLE
        binding.tvCompanyName.visibility = View.INVISIBLE
        binding.etCompanyName.visibility = View.VISIBLE
        binding.tvPosition.visibility = View.INVISIBLE
        binding.etPosition.visibility = View.VISIBLE
        binding.tvCityCompany.visibility = View.INVISIBLE
        binding.etCityCompany.visibility = View.VISIBLE
        binding.tvStart.visibility = View.INVISIBLE
        binding.etStart.visibility = View.VISIBLE
        binding.tvEnd.visibility = View.INVISIBLE
        binding.etEnd.visibility = View.VISIBLE
        binding.tvSalary.visibility = View.INVISIBLE
        binding.etSalary.visibility = View.VISIBLE
    }

    private fun stateSave() {
        binding.ivActionEditPersonalData.visibility = View.VISIBLE
        binding.ivActionSavePersonalData.visibility = View.INVISIBLE
        binding.etFullname.visibility = View.INVISIBLE
        binding.tvFullname.visibility = View.VISIBLE
        binding.etEmail.visibility = View.INVISIBLE
        binding.tvEmail.visibility = View.VISIBLE
        binding.etUsername.visibility = View.INVISIBLE
        binding.tvUsername.visibility = View.VISIBLE
        binding.etPhone.visibility = View.INVISIBLE
        binding.tvPhone.visibility = View.VISIBLE
        binding.etAddress.visibility = View.INVISIBLE
        binding.tvAddress.visibility = View.VISIBLE
        binding.etBirthdate.visibility = View.INVISIBLE
        binding.tvBirthdate.visibility = View.VISIBLE
        binding.rdGroup.visibility = View.INVISIBLE
        binding.tvGender.visibility = View.VISIBLE
        binding.etAbout.visibility = View.INVISIBLE
        binding.tvAbout.visibility = View.VISIBLE
        binding.tvLastEducation.visibility = View.VISIBLE
        binding.etLastEducation.visibility = View.INVISIBLE
        presenter.getDataUserProfile()
    }

    private fun stateSaveWorkExperience(){
        binding.ivActionAddWorkExperience.visibility = View.VISIBLE
        binding.ivActionSaveWorkExperience.visibility = View.INVISIBLE
        binding.tvCompanyName.visibility = View.VISIBLE
        binding.etCompanyName.visibility = View.INVISIBLE
        binding.tvPosition.visibility = View.VISIBLE
        binding.etPosition.visibility = View.INVISIBLE
        binding.tvCityCompany.visibility = View.VISIBLE
        binding.etCityCompany.visibility = View.INVISIBLE
        binding.tvStart.visibility = View.VISIBLE
        binding.etStart.visibility = View.INVISIBLE
        binding.tvEnd.visibility = View.VISIBLE
        binding.etEnd.visibility = View.INVISIBLE
        binding.tvSalary.visibility = View.VISIBLE
        binding.etSalary.visibility = View.INVISIBLE
    }

    private fun stateCancelSave(){
        if ((binding.etFullname.text.toString() == fullname
                && binding.etAddress.text.toString() == address
                && binding.etBirthdate.text.toString() == birthdate
                && binding.etAbout.text.toString() == about && genderRadio == gender && binding.etLastEducation.text.toString() == lastEducation)){
            stateSave()
        } else {
            checkAddDataPersonal()
        }
    }

    private fun stateCancelSaveWorkExperience(){
        if (dataWorkExperience != null){
            if ((binding.etCompanyName.text.toString() == dataWorkExperience?.company
                        && binding.etPosition.text.toString() == dataWorkExperience?.position
                        && binding.etCityCompany.text.toString() == dataWorkExperience?.city
                        && binding.etStart.text.toString() == dataWorkExperience?.dateStart
                        && binding.etEnd.text.toString() == dataWorkExperience?.dateEnd
                        && binding.etSalary.text.toString() == changeFormatMoneyToValue(dataWorkExperience?.salary.toString()))){
                stateSaveWorkExperience()
            } else {
                checkDataworkExperience()
            }
        } else {
                if ((binding.etCompanyName.text.toString() == ""
                        && binding.etPosition.text.toString() == ""
                        && binding.etCityCompany.text.toString() == ""
                        && binding.etStart.text.toString() == ""
                        && binding.etEnd.text.toString() == ""
                        && binding.etSalary.text.toString() == "")){
                stateSaveWorkExperience()
            } else
                checkDataworkExperience()
        }

    }

    private fun checkDataworkExperience(){
        var isEmptyCompany = true
        var isEmptyPosition = true
        var isEmptyCity = true
        var isEmptyStart = true
        var isEmptyEnd = true
        var isEmptySalary = true
        var company = binding.etCompanyName.text.toString()
        var position = binding.etPosition.text.toString()
        var city = binding.etCityCompany.text.toString()
        var start = binding.etStart.text.toString()
        var end = binding.etEnd.text.toString()
        var salary = Helper.changeFormatMoneyToValue(binding.etSalary.text.toString())

        if (company.isNullOrEmpty()){
            isEmptyCompany = true
            binding.etCompanyName.error = getString(R.string.cant_empty)
        } else {
            isEmptyCompany = false
            binding.etCompanyName.error = null
            company = binding.etCompanyName.text.toString()
        }

        if (position.isNullOrEmpty()){
            isEmptyPosition = true
            binding.etPosition.error = getString(R.string.cant_empty)
        } else {
            isEmptyPosition = false
            binding.etPosition.error = null
            position = binding.etPosition.text.toString()
        }

        if (city.isNullOrEmpty()){
            isEmptyCity = true
            binding.etCityCompany.error = getString(R.string.cant_empty)
        } else {
            isEmptyCity = false
            binding.etCityCompany.error = null
            city = binding.etCityCompany.text.toString()
        }

        if (city.isNullOrEmpty()){
            isEmptyCity = true
            binding.etCityCompany.error = getString(R.string.cant_empty)
        } else {
            isEmptyCity = false
            binding.etCityCompany.error = null
            city = binding.etCityCompany.text.toString()
        }

        if (start.isNullOrEmpty()){
            isEmptyStart = true
            binding.etStart.error = getString(R.string.cant_empty)
        } else {
            isEmptyStart = false
            binding.etStart.error = null
            start = binding.etStart.text.toString()
        }

        if (end.isNullOrEmpty()){
            isEmptyEnd = true
            binding.etEnd.error = getString(R.string.cant_empty)
        } else {
            isEmptyEnd = false
            binding.etEnd.error = null
            end = binding.etEnd.text.toString()
        }

        if (salary.isNullOrEmpty()){
            isEmptySalary = true
            binding.etSalary.error = getString(R.string.cant_empty)
        } else {
            isEmptySalary = false
            binding.etSalary.error = null
            salary = binding.etSalary.text.toString()
        }

        if (dataWorkExperience != null){
            if (!isEmptyCompany && !isEmptyPosition && !isEmptyCity && !isEmptyCity && !isEmptyStart && !isEmptyEnd && !isEmptySalary){
                presenter.updateWorkExperience(company, city, start, end, position, ".", Helper.changeFormatMoneyToValue(salary).toInt())
            } else {
                Toast.makeText(applicationContext, getString(R.string.message_fill_data), Toast.LENGTH_SHORT).show()
            }
        } else {
            if (!isEmptyCompany && !isEmptyPosition && !isEmptyCity && !isEmptyCity && !isEmptyStart && !isEmptyEnd && !isEmptySalary){
                presenter.addWorkExperience(company, city, start, end, position, ".", Helper.changeFormatMoneyToValue(salary).toInt())
            } else {
                Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAddDataPersonal(){
        var fullname = binding.etFullname.text.toString()
        var birthdate = binding.etBirthdate.text.toString()
        var genderString = genderRadio
        var address = binding.etAddress.text.toString()
        var about = binding.etAbout.text.toString()

        if (fullname.isEmpty()){
            binding.etFullname.error = getString(R.string.cant_empty)
            isEmptyFullname = true
        } else {
            fullname = binding.etFullname.text.toString()
            isEmptyFullname = false
        }

        if (birthdate.isEmpty()){
            binding.etBirthdate.error = getString(R.string.cant_empty)
            isEmptyBirthdate = true
        } else {
            birthdate = binding.etBirthdate.text.toString()
            isEmptyBirthdate = false
        }

        if (genderString == null){
            binding.rbMale.error = getString(R.string.cant_empty)
            binding.rbFemale.error = getString(R.string.cant_empty)
            isEmptyGender = true
        } else {
            genderString = genderRadio
            isEmptyGender = false
        }

        if (address.isEmpty()){
            binding.etAddress.error = getString(R.string.cant_empty)
            isEmptyAddress = true
        } else {
            address = binding.etAddress.text.toString()
            isEmptyAddress = false
        }

        if (about.isEmpty()){
            binding.etAbout.error = getString(R.string.cant_empty)
            isEmptyAbout = true
        } else {
            about = binding.etAbout.text.toString()
            isEmptyAbout = false
        }

        if (!isEmptyFullname && !isEmptyBirthdate && !isEmptyGender && !isEmptyAddress && !isEmptyAbout){
            presenter.addDataPersonalUser(fullname, birthdate, genderString!!, address, about)
            binding.ivLoading.visibility = View.VISIBLE
            binding.ivActionSavePersonalData.visibility = View.INVISIBLE
        } else {
            Toast.makeText(applicationContext, getString(R.string.message_fill_data), Toast.LENGTH_SHORT).show()
            binding.ivLoading.visibility = View.INVISIBLE
            binding.ivActionSavePersonalData.visibility = View.VISIBLE
        }
    }

    override fun getDocumentResume(item: List<DataDocumentUser?>?) {
        val adapterDocument = AdapterDocumentUser(this, item)
        binding.rvResume.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDocument
        }
    }

    override fun getDocumentPortfolio(item: List<DataDocumentUser?>?) {
        val adapterDocument = AdapterDocumentUser(this, item)
        binding.rvPortop.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDocument
        }
    }

    override fun getDocumentCertificate(item: List<DataDocumentUser?>?) {
        val adapterDocument = AdapterDocumentUser(this, item)
        binding.rvCertificate.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDocument
        }
    }

    override fun getDataUser(data: DataUser?) {
        setDataProfile(data)
        dataWorkExperience = data?.latestWork
        dialogAddEducation(data?.educationDetail)
    }

    override fun getSkillsUser(data: List<DataSkillsUser?>?) {
        val adapterDocument = AdapterSkillsUser(this, data)
        binding.rvSkills.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDocument
            adapterDocument.notifyDataSetChanged()
        }
    }

    override fun getDataCreateVacancy(data: DataCreateVacancy?) {
        popupDialogLastEducation(data?.vacancyEducation)
    }

    private val adapterLastEdu by lazy {
        AdapterDataCreateVacancy(applicationContext, null, arrayListOf(), null, null, null,
            null, null, null, null, this, null, null)
    }

    override fun messageGetDataCreateVacancy(msg: String) {
        Timber.d(msg)
    }

    override fun messageLoadDataPersonal(msg: String) {
        if (msg == "1"){
            binding.swipeRefresh.isRefreshing = false
            presenter.getDataUserProfile()
        }
    }

    override fun messageLoadSkillUser(msg: String) {
        Log.d("loadSkillsUser", msg)
        if (msg == "1"){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageLoadDocumentUser(msg: String) {
        Log.d("loadDetailUser", msg)
    }

    override fun messageAddDataPersonalUser(msg: String) {
        Log.d("DataPersonal", msg)
        if(msg == "1"){
            Toast.makeText(applicationContext, getString(R.string.personal_data_success), Toast.LENGTH_SHORT).show()
            binding.ivLoading.visibility = View.INVISIBLE
            binding.ivActionEditPersonalData.visibility = View.VISIBLE
            stateSave()
        } else {
            Toast.makeText(applicationContext, getString(R.string.personal_data_fail), Toast.LENGTH_SHORT).show()
            binding.ivLoading.visibility = View.INVISIBLE
            binding.ivActionEditPersonalData.visibility = View.VISIBLE
        }
        presenter.getDataUserProfile()
    }

    override fun messageAddLastEducation(msg: String) {
        Timber.d(msg)
        if(msg == "1"){
            Toast.makeText(applicationContext, getString(R.string.add_education_success), Toast.LENGTH_SHORT).show()
            popupAddEducation?.dismiss()
        } else {
            Toast.makeText(applicationContext, getString(R.string.add_education_fail), Toast.LENGTH_SHORT).show()
        }
        presenter.getDataUserProfile()
    }

    override fun messageDeleteSkill(msg: String) {
        //msg notif
        if (msg.contains("Success!")){
            presenter.getSkillsUser()
            Toast.makeText(applicationContext, getString(R.string.delete_skill_success), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, getString(R.string.delete_skill_fail), Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageAddWorkExperience(msg: String) {
        //msg notif
        Timber.d(msg)
        if(msg == "1"){
            Toast.makeText(applicationContext, getString(R.string.add_work_success), Toast.LENGTH_SHORT).show()
            stateSaveWorkExperience()
        } else {
            Toast.makeText(applicationContext, getString(R.string.add_work_fail), Toast.LENGTH_SHORT).show()
            stateEditWorkExperience()
        }
        presenter.getDataUserProfile()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listSkillsClick(view: View, data: VacancySkillItem) {
        TODO("Not yet implemented")
    }

    override fun listEduClick(view: View, data: VacancyEducationItem) {
        when(view.id){
            R.id.relative_click -> {
                popupDialogLastEdu?.dismiss()
                idEdu = data.id!!
                etLastEdu?.setText(data.name)
                if (idEdu == 1){
                    linearGpa?.visibility = View.GONE
                    etGpa?.setText("0.0")
                } else {
                    linearGpa?.visibility = View.VISIBLE
                    etGpa?.setText(null)
                }
            }
        }
    }

    override fun listChipsSkillClick(view: View, data: VacancySkillItem) {
        TODO("Not yet implemented")
    }

}