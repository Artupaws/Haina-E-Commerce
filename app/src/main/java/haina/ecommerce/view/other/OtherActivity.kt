package haina.ecommerce.view.other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.service.AdapterServiceCategory
import haina.ecommerce.databinding.ActivityOtherBinding
import haina.ecommerce.model.service.DataService
import haina.ecommerce.view.electricity.ElectricityActivity
import haina.ecommerce.view.housephone.HousePhoneActivity
import haina.ecommerce.view.internetandtv.InternetActivity
import haina.ecommerce.view.postpaid.PostpaidActivity
import haina.ecommerce.view.topup.TopupActivity
import haina.ecommerce.view.topup.electronicmoney.ElectronicMoneyActivity
import haina.ecommerce.view.water.WaterActivity
import java.sql.RowId

class OtherActivity : AppCompatActivity(), OtherContract {

    private lateinit var binding: ActivityOtherBinding
    private lateinit var presenter: OtherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OtherPresenter(this, this)
        presenter.getListService()
        binding.toolbarOther.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarOther.title = getString(R.string.billing_title)
        binding.toolbarOther.setNavigationOnClickListener {
            onBackPressed()
        }
        refresh()
    }

    private fun refresh() {
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.frameShimmer.visibility = View.VISIBLE
            binding.rvServiceCategory.visibility = View.GONE
            presenter.getListService()
            binding.swipeRefresh.isRefreshing = false
        })
    }

    override fun messageGetListService(msg: String) {
        Log.d("getPresenter", msg)
        binding.swipeRefresh.isRefreshing = false
        if (!msg.contains("Success")){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getListService(data: List<DataService?>?) {
        if (!data.isNullOrEmpty()){
            binding.frameShimmer.visibility = View.GONE
            binding.rvServiceCategory.apply {
                adapter = AdapterServiceCategory(applicationContext, data)
                layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            }
            binding.rvServiceCategory.visibility = View.VISIBLE
        }

    }
}