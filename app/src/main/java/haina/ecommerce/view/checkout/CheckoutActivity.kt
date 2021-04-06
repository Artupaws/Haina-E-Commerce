package haina.ecommerce.view.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityCheckoutBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.paymentmethod.PaymentActivity

class CheckoutActivity : AppCompatActivity(), View.OnClickListener, CheckoutContract {

    private lateinit var binding: ActivityCheckoutBinding
    private var titleService:String? = null
    private var customerNumber:String? = null
    private var idProduct:Int? = null
    private val helper:Helper = Helper()
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var presenter: CheckoutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CheckoutPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)

        binding.toolbarCheckout.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarCheckout.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarCheckout.title = "Checkout"
        binding.btnPayment.setOnClickListener(this)

        customerNumber = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER_PULSA)
        binding.tvNumber.text = customerNumber
        binding.tvTotalPay.text = intent.getStringExtra("totalPrice")
        binding.tvPrice.text = intent.getStringExtra("totalPrice")
        titleService = intent.getStringExtra("titleService")
        idProduct = intent.getIntExtra("idProduct", 0)
        binding.tvServiceType.text = intent.getStringExtra("serviceType")
        binding.tvTitleService.text = titleService
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_payment -> {
                presenter.checkout(customerNumber!!, idProduct!!)
            }
        }
    }

    private fun move(status:String){
        if (status.contains("Success")){
            val intent = Intent(applicationContext, PaymentActivity::class.java)
                    .putExtra("idProduct", idProduct)
                    .putExtra("serviceType", binding.tvServiceType.text.toString())
                    .putExtra("numberCustomer", binding.tvNumber.text.toString())
                    .putExtra("nominal", binding.tvPrice.text.toString())
                    .putExtra("titleService", binding.tvTitleService.text.toString())
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext, status, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageCheckout(msg: String) {
        Log.d("checkout", msg)
        move(msg)
    }
}