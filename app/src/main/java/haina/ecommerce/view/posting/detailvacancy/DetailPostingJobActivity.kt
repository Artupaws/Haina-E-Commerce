package haina.ecommerce.view.posting.detailvacancy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterApplicantJob
import haina.ecommerce.adapter.AdapterSkillsRequires
import haina.ecommerce.databinding.ActivityDetailPostingJobBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.JobapplicantItem
import haina.ecommerce.view.posting.vacancy.VacancyContract

class DetailPostingJobActivity : AppCompatActivity(),View.OnClickListener, DetailPostingJobContract {

    private lateinit var binding: ActivityDetailPostingJobBinding
    private lateinit var presenter: DetailPostingJobPresenter
    private val helper:Helper = Helper
    private var idJobVacancy:Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostingJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailPostingJobPresenter(this, this)
        binding.toolbarDetailJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailJob.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailJob.title = "Detail Job Vacancy"
        val item = intent.getParcelableExtra<DataMyJob>("item")
        if (item?.skill?.size == 0){
            binding.includeEmptySkill.clEmptySkill.visibility = View.VISIBLE
            binding.rvSkills.visibility = View.GONE
        } else {
            binding.includeEmptySkill.clEmptySkill.visibility = View.GONE
            binding.rvSkills.visibility = View.VISIBLE
        }
        idJobVacancy = item?.id
        binding.etTitleJob.setText(item?.title)
        binding.etCategoryJob.setText(item?.jobcategory)
        binding.etDescriptionJob.setText(item?.description)
        binding.etAddressCompany.setText(item?.address?.address)
        binding.etSalaryFrom.setText(helper.convertToFormatMoneySalary(item?.salaryFrom.toString()))
        binding.etSalaryTo.setText(helper.convertToFormatMoneySalary(item?.salaryTo.toString()))
        Glide.with(this).load(item?.photoUrl).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.NONE).into(binding.ivCompany)
        binding.rvSkills.apply {
            val adapterSkillRequires = AdapterSkillsRequires(applicationContext, item?.skill)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterSkillRequires
            adapterSkillRequires.notifyDataSetChanged()
        }
        presenter.getListJobApplicant(idJobVacancy!!)
        refresh()

    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListJobApplicant(idJobVacancy!!)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){

        }
    }

    override fun getListJobApplicant(item: List<JobapplicantItem?>?) {
        binding.rvApplicant.apply {
            val adapterApplicant = AdapterApplicantJob(applicationContext, item)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterApplicant
            adapterApplicant.notifyDataSetChanged()
        }
    }

    override fun messageGetListSuccess(msg: String) {
        Log.d("jobApplicantSuccess", msg)
        if (msg.contains("Success")){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageGetListFailed(msg: String) {
        Log.d("jobApplicantFailed", msg)
    }
}