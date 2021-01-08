package haina.ecommerce.view.posting.applyapplicant

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityApplyApplicantBinding
import haina.ecommerce.model.JobapplicantItem

class ApplyApplicantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyApplicantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarApplyApplicant.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarApplyApplicant.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarApplyApplicant.title = "Apply Applicant"

        val item  = intent.getParcelableExtra<JobapplicantItem>("dataApplicant")
        binding.tvFullname.text = item?.fullname
        binding.tvEmail.text = item?.email
        binding.tvPhone.text = item?.phone
        binding.tvGender.text = item?.gender
        binding.tvAbout.text = item?.about
        binding.tvTitleDocument.text = item?.userDocument?.docsName
        Glide.with(this).load(item?.photo).skipMemoryCache(false).diskCacheStrategy(
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
}