package haina.ecommerce.view.history.historysubmitapplication.detailapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.barteksc.pdfviewer.PDFView
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailApplicationBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.model.vacancy.DataApplicationDetail
import haina.ecommerce.model.vacancy.MyApplication
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.applyjob.ApplyJobActivity
import haina.ecommerce.view.pdf.PdfViewActivity
import haina.ecommerce.view.webview.WebViewActivity
import timber.log.Timber

class DetailApplicationActivity : AppCompatActivity(), DetailApplicationContract {

    private lateinit var binding: ActivityDetailApplicationBinding
    private lateinit var presenter: DetailApplicationPresenter
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

    private var applicationData:MyApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        sharePref = SharedPreferenceHelper(this)
        presenter = DetailApplicationPresenter(this, this)
        listParams = ArrayList()
        applicationData = intent.getParcelableExtra("applicationData")
        presenter.getApplicationDetail(applicationData!!.id!!)

    }

    private fun setData(data:DataApplicationDetail){
        val item = data.vacancy

        idJobVacancy = item?.id
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
        if (item?.salaryDisplay == 1){
            salary = "${Helper.convertToFormatMoneyIDRFilter(item.minSalary.toString())}-${Helper.convertToFormatMoneyIDRFilter(item.maxSalary.toString())}"
            binding.tvSalary.text = salary
        } else {
            binding.tvSalary.text = "Salary hidden"
        }
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


        val nameDocument = data.resume!!.docsName
        binding.tvTitleDocument.text = nameDocument
        binding.layoutResume.setOnClickListener {
            val intent = Intent(this, PdfViewActivity::class.java)
            intent.putExtra("url", data.resume!!.docsUrl)
            startActivity(intent)
        }

        binding.tvApplicationStatus.text = data.status

        if(data.status.equals("interview")){
            binding.llInterview.visibility = View.VISIBLE

            binding.tvInterview.text = data.interview?.method
            binding.tvInterviewDate.text = data.interview?.time
            binding.etInterviewContactPerson.text = data.interview?.cpName
            binding.etInterviewContactNumber.text = data.interview?.cpPhone
            binding.etInterviewLocation.text = data.interview?.location
        }

    }

    override fun messageGetApplicationDetail(msg: String) {
        Timber.d(msg)
    }

    override fun getApplicationDetail(data: DataApplicationDetail) {
        setData(data)
    }


}