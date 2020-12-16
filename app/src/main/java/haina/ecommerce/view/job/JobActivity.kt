package haina.ecommerce.view.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobCategoryOnJob
import haina.ecommerce.adapter.AdapterJobVacancy
import haina.ecommerce.databinding.ActivityJobBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob

class JobActivity : AppCompatActivity(), JobContract, View.OnClickListener{

    private lateinit var binding: ActivityJobBinding
    private lateinit var presenter: JobPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = JobPresenter(this)
        presenter.loadListJobCategory()
        presenter.loadListJobVacancy()
        binding.toolbarJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarJob.setNavigationOnClickListener { onBackPressed() }
    }

    override fun successLoadListJob(msg: String) {
        Log.d("loadlistJobSuccess", msg)
    }

    override fun errorLoadListJob(msg: String) {
        Log.d("loadlistJobError", msg)
    }

    override fun getLoadListJob(list: List<DataItemJob?>?) {
        val listJobAdapter = AdapterJobVacancy(this, list)
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listJobAdapter
            listJobAdapter.notifyDataSetChanged()
        }
    }

    override fun successLoadJobCategory(msg: String) {
        Log.d("loadJobCategorySuccess", msg)
    }

    override fun errorLoadJobCategory(msg: String) {
        Log.d("loadJobCategoryError", msg)
    }

    override fun getLoadJobCategory(itemHaina: List<DataItemHaina?>?) {
        val jobCategoryAdapter = AdapterJobCategoryOnJob(this, itemHaina)
        binding.rvCategoryJob.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = jobCategoryAdapter
            jobCategoryAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}