package haina.ecommerce.view.detailjob

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import coil.imageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailJobBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.DataItemJob
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.applyjob.ApplyJobActivity

class DetailJobActivity : AppCompatActivity(), View.OnClickListener, DetailJobContract {

    private lateinit var binding: ActivityDetailJobBinding
    private lateinit var presenter: DetailJobPresenter
    private lateinit var sharePref: SharedPreferenceHelper
    private var helper: Helper = Helper
    private var broadcaster: LocalBroadcastManager? = null
    private var saveJob:String =""
    var idJobVacancy:Int? = 0
    var refresh:String? = null
    var salary:String = "0"
    private lateinit var listParams: ArrayList<String>
    private var  imagesListener : ImageListener? = null
    private var idCompany:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        sharePref = SharedPreferenceHelper(this)
        presenter = DetailJobPresenter(this, this)
        listParams = ArrayList()
        val item = intent.getParcelableExtra<DataAllVacancy>("detailJob")
        idCompany = intent.getIntExtra("idCompany", 0)
        idJobVacancy = item?.id
        idJobVacancy?.let { presenter.checkAppliedJob(it) }
        binding.tvTitleJob.text = item?.position
        binding.tvCompanyName.text = item?.companyName
        binding.tvAddress.text = item?.address
        binding.tvLocation.text = item?.cityName
        binding.tvDescriptionJob.text = item?.description
        binding.tvPositionLevel.text = item?.position
        binding.tvQualification.text = item?.eduName
        binding.tvYearExperience.text = item?.experience.toString()
        binding.tvEmploymentType.text = item?.typeName
        binding.tvSpecialization.text = item?.specialistName
        salary ="${helper.convertToFormatMoneySalary(item?.minSalary.toString())} - ${helper.convertToFormatMoneySalary(item?.maxSalary.toString())}"
        binding.tvSalary.text = salary
        binding.tvDatePublish.text = dateFormat(item?.createdAt)
        Glide.with(applicationContext).load(item?.photoCompany).skipMemoryCache(true).diskCacheStrategy(
                DiskCacheStrategy.NONE)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        binding.progressCircular.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        binding.progressCircular.visibility = View.GONE
                        return false
                    }

                })
                .into(binding.ivImageCompany)
        if (item?.companyPhoto != null){
            for (i in item.companyPhoto){
                i?.photoUrl.let { listParams.add(it!!) }
                binding.vpImageProperty.pageCount = listParams.size
                imagesListener = ImageListener{
                    position, imageView ->
                    Glide.with(this).load(listParams[position]).placeholder(R.drawable.ic_empty).into(imageView)
                }
                binding.vpImageProperty.setImageListener(imagesListener)
            }
        }
        binding.toolbarDetailJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailJob.setNavigationOnClickListener{onBackPressed()}
        binding.ivSaveJob.setOnClickListener(this)
        binding.btnApply.setOnClickListener(this)
        toggleSaveJob()
        setStateButtonApply(idCompany, item!!.idCompany!!)
    }

    private fun setStateButtonApply(idCompanyParams:Int, idCompanyVacancy:Int){
        binding.btnApply.isEnabled = idCompanyParams != idCompanyVacancy
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_apply -> {
                val intent = Intent(applicationContext, ApplyJobActivity::class.java)
                        .putExtra("titleJob", intent.getStringExtra("title"))
                        .putExtra("idJobVacancy", idJobVacancy)
                startActivity(intent)
            }
            R.id.iv_save_job -> {
                if (sharePref.getValueBoolien(Constants.PREF_IS_LOGIN)){
                    toggleSaveJob()
                } else {
                    binding.ivSaveJob.isChecked = false
                    Toast.makeText(applicationContext, "Login first for saving this job", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toggleSaveJob(){
        binding.ivSaveJob.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "On", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Off", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun checkLogin(msg: String) {
//        Log.d("checkLoginOnDetailJob", msg)
//        if (sharePref.getValueBoolien(Constants.PREF_IS_LOGIN) && msg.contains("Available!")) {
//            binding.btnApply.isEnabled = true
//        } else {
//            binding.btnApply.isEnabled = false
//            Toast.makeText(applicationContext, "Please login for apply this job!", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onResume() {
        super.onResume()
        idJobVacancy?.let { presenter.checkAppliedJob(it) }
    }

    override fun messageCheckAppliedJob(msg: String) {
        Log.d("mesageAvailableJob", msg)
//        checkLogin(msg)
    }

}