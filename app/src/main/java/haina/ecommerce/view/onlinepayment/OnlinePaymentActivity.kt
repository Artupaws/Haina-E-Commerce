package haina.ecommerce.view.onlinepayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityOnlinePaymentBinding

class OnlinePaymentActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOnlinePaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlinePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}