package haina.ecommerce.view.hotels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityHotelBaseBinding

class HotelBaseActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHotelBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar4.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}