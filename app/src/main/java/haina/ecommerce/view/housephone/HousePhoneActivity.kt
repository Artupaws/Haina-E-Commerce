package haina.ecommerce.view.housephone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityHousePhoneBinding

class HousePhoneActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHousePhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHousePhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarHousePhone.title = "Telkom"
        binding.toolbarHousePhone.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHousePhone.setNavigationOnClickListener { onBackPressed() }
    }
}