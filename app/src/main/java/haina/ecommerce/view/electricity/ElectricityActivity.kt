package haina.ecommerce.view.electricity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityElectricityBinding

class ElectricityActivity : AppCompatActivity() {

    private lateinit var binding:ActivityElectricityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElectricityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarElectricity.title = "PLN Electricity"
        binding.toolbarElectricity.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarElectricity.setNavigationOnClickListener { onBackPressed() }

    }
}