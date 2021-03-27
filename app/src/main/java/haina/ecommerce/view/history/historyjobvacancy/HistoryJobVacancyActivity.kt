package haina.ecommerce.view.history.historyjobvacancy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.adapter.TabAdapterHistoryJobVacancy
import haina.ecommerce.databinding.ActivityHistoryJobVacancyBinding

class HistoryJobVacancyActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHistoryJobVacancyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryJobVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarHistory.title = "History Job Vacancy"
        binding.viewPagerHistory.adapter = TabAdapterHistoryJobVacancy(supportFragmentManager, 0)
        binding.tabLayoutHistory.setupWithViewPager(binding.viewPagerHistory)
        binding.toolbarHistory.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHistory.setNavigationOnClickListener { onBackPressed() }

    }
}