package haina.ecommerce.view.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityCheckoutBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.view.payment.PaymentActivity

class CheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCheckoutBinding
    private var titleService:String? = null
    private val helper:Helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCheckout.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarCheckout.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarCheckout.title = "Checkout"
        binding.btnPayment.setOnClickListener(this)

        binding.tvNumber.text = intent.getStringExtra("phoneNumber")
        binding.tvTotalPay.text = intent.getStringExtra("totalPrice")
        binding.tvPrice.text = intent.getStringExtra("totalPrice")
        titleService = intent.getStringExtra("titleService")
        binding.tvTitleService.text = titleService
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_payment -> {
                val intent = Intent(applicationContext, PaymentActivity::class.java)
                    .putExtra("serviceType", binding.tvServiceType.text.toString())
                    .putExtra("numberCustomer", binding.tvNumber.text.toString())
                    .putExtra("nominal", binding.tvPrice.text.toString())
                    .putExtra("titleService", binding.tvTitleService.text.toString())
                startActivity(intent)
            }
        }
    }
}