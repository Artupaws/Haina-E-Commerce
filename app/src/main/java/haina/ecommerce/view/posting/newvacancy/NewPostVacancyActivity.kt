package haina.ecommerce.view.posting.newvacancy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobPosting
import haina.ecommerce.databinding.ActivityPostNewVacancyBinding
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class NewPostVacancyActivity : AppCompatActivity(), VacancyContract, View.OnClickListener {

    private lateinit var binding:ActivityPostNewVacancyBinding
    private lateinit var presenter: VacancyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNewVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener(this)
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getDataMyPost()
        })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login_not_login -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_next -> {
                startActivity(Intent(applicationContext, SkillAndEducationActivity::class.java))
            }
        }
    }

    override fun successLoadMyPost(msg: String) {
        Log.d("MyPost", msg)
        if (msg.isEmpty()) {
//            binding.rvJobVacancy.visibility = View.INVISIBLE
//            binding.includeEmpty.tvEmpty.text = "You haven't posted anything yet"
//            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
//            binding.rvJobVacancy.visibility = View.VISIBLE
//            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun errorLoadMyPost(msg: String) {
        Log.d("errorLoadPost", msg)
    }

    override fun getListMyPost(list: List<DataMyJob?>?) {
        val getMyPost = AdapterJobPosting(applicationContext, list)
//        binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
//        binding.rvJobVacancy.apply {
//            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//            adapter = getMyPost
//        }
    }
}