package haina.ecommerce.view.posting.applyapplicant

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityApplyApplicantBinding
import haina.ecommerce.model.JobapplicantItem

class ApplyApplicantActivity : AppCompatActivity(), View.OnClickListener, ApplyApplicantContract {

    private lateinit var binding: ActivityApplyApplicantBinding
    private val statusShortlist:String = "shortlisted"
    private var idApplicant:Int = 0
    private lateinit var presenter: ApplyApplicantPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarApplyApplicant.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarApplyApplicant.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarApplyApplicant.title = "Apply Applicant"
        binding.btnShortList.setOnClickListener(this)
        presenter = ApplyApplicantPresenter(this, this)

        val item  = intent.getParcelableExtra<JobapplicantItem>("dataApplicant")
        binding.tvApplicationStatus.text = item?.status
        idApplicant = item?.id!!
        if (binding.tvApplicationStatus.text != "pending"){
            binding.btnShortList.isEnabled = false
            binding.btnDecline.isEnabled = false
        }
        binding.tvFullname.text = item.fullname
        binding.tvEmail.text = item.email
        binding.tvPhone.text = item.phone
        binding.tvGender.text = item.gender
        binding.tvAbout.text = item.about
        binding.tvTitleDocument.text = item.userDocument?.docsName
        Glide.with(this).load(item.photo).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.NONE).listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.progressCircular.visibility = View.INVISIBLE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                binding.progressCircular.visibility = View.INVISIBLE
                return false
            }
        }).into(binding.ivApplicant)
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_short_list -> {
                presenter.addShortlistApplicant(idApplicant, statusShortlist)
            }
            R.id.btn_decline ->{

            }
        }
    }

    override fun messageAddShortlistApplicant(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Success")){
            binding.btnShortList.isEnabled = false
            binding.btnDecline.isEnabled = false
        }
    }
}