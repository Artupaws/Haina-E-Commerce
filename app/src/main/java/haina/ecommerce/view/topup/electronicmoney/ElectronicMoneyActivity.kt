package haina.ecommerce.view.topup.electronicmoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityElectricityBinding
import haina.ecommerce.databinding.ActivityElectronicMoneyBinding

class ElectronicMoneyActivity : AppCompatActivity() {

    private lateinit var binding:ActivityElectronicMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElectronicMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarElectronicMoney.title = "Electronic Money"
        binding.toolbarElectronicMoney.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarElectronicMoney.setNavigationOnClickListener { onBackPressed() }

    }
}