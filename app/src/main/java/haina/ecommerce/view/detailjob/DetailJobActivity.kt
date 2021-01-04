package haina.ecommerce.view.detailjob

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobLocation
import haina.ecommerce.databinding.ActivityDetailJobBinding
import haina.ecommerce.model.DataItemHaina

class DetailJobActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailJobBinding
    private var saveJob:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetailJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailJob.setNavigationOnClickListener{onBackPressed()}
        binding.ivSaveJob.setOnClickListener(this)
        setTextDetailJob()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_save_job -> {
                saveJob = "job saved"
                setSaveJobState()
            }

        }
    }

    private fun setSaveJobState(){
        if(saveJob == "job saved"){
            binding.ivSaveJob.setImageResource(R.drawable.ic_bookmarkfill)
            val snackbar = Snackbar.make(binding.ivSaveJob, "Job Saved", Snackbar.LENGTH_SHORT)
                .setAction("Close", null)
            snackbar.show()
        } else {
            binding.ivSaveJob.setImageResource(R.drawable.ic_bookmark_empty)
        }
    }

    private fun setTextDetailJob(){
        val titleJob:String? = intent.getStringExtra("title")
        val companyName:String? = intent.getStringExtra("nameCompany")
        val description:String? = intent.getStringExtra("description")
        val salary:String? = intent.getStringExtra("salary")
        val location:String? = intent.getStringExtra("location")
        val datePublish:String? = intent.getStringExtra("datePublish")
        val jobCategory:String? = intent.getStringExtra("jobCategory")
        val imageCompany:String? = intent.getStringExtra("imageCompany")
        binding.tvTitleJob.text = titleJob
        binding.tvCompanyName.text = companyName
        binding.tvLocationJob.text = location
        binding.tvDescriptionJob.text = description
        binding.tvSalary.text = salary
        binding.tvDatePublish.text = datePublish
        binding.tvJobCategory.text = jobCategory
        Glide.with(applicationContext).load(imageCompany).skipMemoryCache(true).diskCacheStrategy(
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
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

}