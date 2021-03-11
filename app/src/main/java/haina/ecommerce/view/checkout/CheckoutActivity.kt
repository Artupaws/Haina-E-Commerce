package haina.ecommerce.view.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private var titleService:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCheckout.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarCheckout.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarCheckout.title = "Checkout"

        binding.tvNumber.text = intent.getStringExtra("phoneNumber")
        binding.tvTotalPay.text = intent.getStringExtra("totalPrice")
        binding.tvPrice.text = intent.getStringExtra("totalPrice")
        titleService = intent.getStringExtra("titleService")
        binding.tvTitleService.text = titleService

    }
}