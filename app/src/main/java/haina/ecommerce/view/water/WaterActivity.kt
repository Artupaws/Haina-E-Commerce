package haina.ecommerce.view.water

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityWaterBinding

class WaterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWaterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarWater.title = "Water PDAM"
        binding.toolbarWater.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarWater.setNavigationOnClickListener { onBackPressed() }
    }
}