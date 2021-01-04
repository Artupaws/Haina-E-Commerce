package haina.ecommerce.view.myaccount.detailaccount

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
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
import haina.ecommerce.databinding.ActivityDetailAccountBinding
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.myaccount.addrequirement.AddRequirementActivity
import java.util.*


class DetailAccountActivity : AppCompatActivity(), View.OnClickListener, DetailAccountContract {

    private lateinit var binding: ActivityDetailAccountBinding
    private lateinit var presenter: DetailAccountPresenter
    lateinit var sharedPref: SharedPreferenceHelper
    private var dateSetListener: OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailAccountPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        setDataProfile()
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

    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            setDataProfile()
            loadAllDocument()
        })
    }

    private fun loadAllDocument(){
        presenter.loadDocumentResume(1)
        presenter.loadDocumentPortfolio(2)
        presenter.loadDocumentCertificate(3)
    }

    private fun setDataProfile() {
        Glide.with(this).load(sharedPref.getValueString(Constants.PREF_PHOTO))
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
        binding.tvFullname.text = sharedPref.getValueString(Constants.PREF_FULLNAME)
        binding.etFullname.setText(sharedPref.getValueString(Constants.PREF_FULLNAME))
        binding.tvEmail.text = sharedPref.getValueString(Constants.PREF_EMAIL)
        binding.etEmail.setText(sharedPref.getValueString(Constants.PREF_EMAIL))
        binding.tvUsername.text = sharedPref.getValueString(Constants.PREF_USERNAME)
        binding.etUsername.setText(sharedPref.getValueString(Constants.PREF_USERNAME))
        binding.tvPhone.text = sharedPref.getValueString(Constants.PREF_PHONE)
        binding.etPhone.setText(sharedPref.getValueString(Constants.PREF_PHONE))
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_action_edit_personal_data -> {
                stateEdit()
            }
            R.id.iv_action_save_personal_data -> {
                stateSave()
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
                Log.d("date", "onDateSet: mm/dd/yyy: $month/$day/$year")
                val date = "$month/$day/$year"
                binding.tvBirthdate.text = date
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
        Toast.makeText(applicationContext, radio.text, Toast.LENGTH_SHORT).show()
        binding.tvGender.text = radio.text
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

    override fun messageLoadDetailUser(msg: String) {
        Log.d("loadDetailUser", msg)
    }

}