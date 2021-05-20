package haina.ecommerce.view.other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
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

class OtherActivity : AppCompatActivity(), View.OnClickListener, OtherContract {

    private lateinit var binding: ActivityOtherBinding
    private lateinit var presenter: OtherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = OtherPresenter(this, this)
        presenter.getListService()
        binding.toolbarOther.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarOther.title = "All Categories"
        binding.toolbarOther.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.linearTopup.setOnClickListener(this)
        binding.linearElectricity.setOnClickListener(this)
        binding.linearInternetTvServices.setOnClickListener(this)
        binding.linearPhoneHouse.setOnClickListener(this)
        binding.linearPostpaid.setOnClickListener(this)
        binding.linearWater.setOnClickListener(this)
        binding.linearElectronicMoney.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.linear_topup -> {
                val intent = Intent(applicationContext, TopupActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_internet_tv_services -> {
                val intent = Intent(applicationContext, InternetActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_electricity -> {
                val intent = Intent(applicationContext, ElectricityActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_water -> {
                val intent = Intent(applicationContext, WaterActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_postpaid -> {
                val intent = Intent(applicationContext, PostpaidActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_phone_house -> {
                val intent = Intent(applicationContext, HousePhoneActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_electronic_money -> {
                val intent = Intent(applicationContext, ElectronicMoneyActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun messageGetListService(msg: String) {
        Log.d("getPresenter", msg)
    }

    override fun getListService(data: List<DataService?>?) {
        binding.rvServiceCategory.apply {
            adapter = AdapterServiceCategory(applicationContext, data)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }
}