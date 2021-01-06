package haina.ecommerce.view.posting.detailvacancy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterAddressCompany
import haina.ecommerce.adapter.AdapterApplicantJob
import haina.ecommerce.databinding.ActivityDetailPostingJobBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataMyJob

class DetailPostingJobActivity : AppCompatActivity(), DetailPostingJobContract, View.OnClickListener {

    private lateinit var binding: ActivityDetailPostingJobBinding
    private lateinit var presenter: DetailPostingJobPresenter
    private val helper:Helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostingJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailPostingJobPresenter(this, this)
        binding.toolbarDetailJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailJob.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailJob.title = "Detail Job Vacancy"
        val item = intent.getParcelableExtra<DataMyJob>("item")
        binding.etTitleJob.setText(item?.title)
        binding.etCategoryJob.setText(item?.jobcategory)
        binding.etDescriptionJob.setText(item?.description)
        binding.etAddressCompany.setText(item?.address?.address)
        binding.etSalaryFrom.setText(helper.convertToFormatMoneySalary(item?.salaryFrom.toString()))
        binding.etSalaryTo.setText(helper.convertToFormatMoneySalary(item?.salaryTo.toString()))
        Glide.with(this).load(item?.photoUrl).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.NONE).into(binding.ivCompany)
        binding.rvApplicant.apply {
            val adapterApplicant = AdapterApplicantJob(applicationContext, item?.jobapplicant)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterApplicant
            adapterApplicant.notifyDataSetChanged()
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){

        }
    }
}