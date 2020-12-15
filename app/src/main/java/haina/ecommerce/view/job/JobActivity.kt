package haina.ecommerce.view.job

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.AdapterJobCategory
import haina.ecommerce.adapter.AdapterJobCategoryOnJob
import haina.ecommerce.databinding.ActivityJobBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataJobVacancy

class JobActivity : AppCompatActivity(), JobContract, View.OnClickListener{

    private lateinit var binding: ActivityJobBinding
    private lateinit var presenter: JobPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = JobPresenter(this)
        presenter.loadListJobCategory()
    }

    override fun successLoadListJob(msg: String) {
        Log.d("loadlistJobSuccess", msg)
    }

    override fun errorLoadListJob(msg: String) {
        Log.d("loadlistJobError", msg)
    }

    override fun getLoadListJob(list: List<DataJobVacancy?>?) {
        Log.d("loadlistJobSuccess", list.toString())
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