package haina.ecommerce.view.myaccount.detailaccount

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import haina.ecommerce.databinding.ActivityDetailAccountBinding
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.model.DataSkillsUser
import haina.ecommerce.model.DataUser
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.view.myaccount.addrequirement.AddRequirementActivity
import haina.ecommerce.view.myaccount.addskills.AddSkillsActivity
import java.util.*

class DetailAccountActivity : AppCompatActivity(), View.OnClickListener, DetailAccountContract {

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
    private var username:String? = null
    var refresh:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = DetailAccountPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        presenter.getDataUserProfile()
        presenter.getSkillsUser()
        radioGrroup()
        setTextBirthDate()
        refresh()
        loadAllDocument()

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

    private fun setDataProfile(data: DataUser?) {
        fullname = data?.fullname
        email = data?.email
        username = data?.username
        phone = data?.phone
        address = data?.address
        birthdate = data?.birthdate
        gender = data?.gender
        about = data?.about
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
        binding.tvAbout.text = data?.about
        binding.etAbout.setText(binding.tvAbout.text)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_action_edit_personal_data -> {
                stateEdit()
            }
            R.id.iv_action_save_personal_data -> {
                stateCancelSave()
            }
            R.id.et_birthdate -> {
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
        dateSetListener = OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month += 1
                Log.d("date", "onDateSet: yyyy/mm/dd: $year-$month-$day")
                val date = "$year-$month-$day"
                binding.etBirthdate.setText(date)
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

    private fun stateCancelSave(){
        if ((binding.etFullname.text.toString() == fullname
                && binding.etAddress.text.toString() == address
                && binding.etBirthdate.text.toString() == birthdate
                && binding.etAbout.text.toString() == about
                        && genderRadio == gender)){
            stateSave()
        } else {
            checkAddDataPersonal()
        }
    }

    private fun checkAddDataPersonal(){
        var fullname = binding.etFullname.text.toString()
        var birthdate = binding.etBirthdate.text.toString()
        var genderString = genderRadio
        var address = binding.etAddress.text.toString()
        var about = binding.etAbout.text.toString()

        if (fullname.isEmpty()){
            binding.etFullname.error = "fullname can't be empty"
            isEmptyFullname = true
        } else {
            fullname = binding.etFullname.text.toString()
            isEmptyFullname = false
        }

        if (birthdate.isEmpty()){
            binding.etBirthdate.error = "birthdate can't be empty"
            isEmptyBirthdate = true
        } else {
            birthdate = binding.etBirthdate.text.toString()
            isEmptyBirthdate = false
        }

        if (genderString == null){
            binding.rbMale.error = "gender can't be empty"
            binding.rbFemale.error = "gender can't be empty"
            isEmptyGender = true
        } else {
            genderString = genderRadio
            isEmptyGender = false
        }

        if (address.isEmpty()){
            binding.etAddress.error = "address can't be empty"
            isEmptyAddress = true
        } else {
            address = binding.etAddress.text.toString()
            isEmptyAddress = false
        }

        if (about.isEmpty()){
            binding.etAbout.error = "about can't be empty"
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
            Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
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
    }

    override fun getSkillsUser(data: List<DataSkillsUser?>?) {
        val adapterDocument = AdapterSkillsUser(this, data)
        binding.rvSkills.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDocument
            adapterDocument.notifyDataSetChanged()
        }
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
            Toast.makeText(applicationContext, "Success Add Data Personal", Toast.LENGTH_SHORT).show()
            binding.ivLoading.visibility = View.INVISIBLE
            binding.ivActionEditPersonalData.visibility = View.VISIBLE
            stateSave()
        } else {
            Toast.makeText(applicationContext, "Failed Add Data Personal", Toast.LENGTH_SHORT).show()
            binding.ivLoading.visibility = View.INVISIBLE
            binding.ivActionEditPersonalData.visibility = View.VISIBLE
        }
    }

    override fun messageDeleteSkill(msg: String) {
        if (msg.contains("Success!")){
            presenter.getSkillsUser()
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

}