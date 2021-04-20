package haina.ecommerce.view.flight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityFlightTicketBinding

class FlightTicketActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFlightTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}