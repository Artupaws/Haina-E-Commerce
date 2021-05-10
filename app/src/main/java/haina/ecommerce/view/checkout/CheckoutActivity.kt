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
import haina.ecommerce.model.pulsaanddata.RequestPulsa
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.paymentmethod.PaymentActivity

class CheckoutActivity : AppCompatActivity(), View.OnClickListener, CheckoutContract {

    private lateinit var binding: ActivityCheckoutBinding
    private var titleService:String? = null
    private var customerNumber:String? = null
    private var idProduct:Int? = null
    private val helper:Helper = Helper
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var presenter: CheckoutPresenter
    private var typeTransaction:Int = 0
    private lateinit var requestPulsa:RequestPulsa

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
        val typeTransactionParams = intent.getIntExtra("typeTransaction", 0)
        typeTransaction(typeTransactionParams)
//        binding.tvTotalPay.text = intent.getStringExtra("totalPrice")
//        binding.tvPrice.text = intent.getStringExtra("totalPrice")
//        titleService = intent.getStringExtra("titleService")
//        idProduct = intent.getIntExtra("idProduct", 0)
//        binding.tvServiceType.text = intent.getStringExtra("serviceType")
//        binding.tvTitleService.text = titleService
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_payment -> {
                when(typeTransaction){
                    1 -> {
                        presenter.checkout(requestPulsa.phoneNumber, requestPulsa.idProduct)
                    }
                    2 -> {
                        presenter.checkout(requestPulsa.phoneNumber, requestPulsa.idProduct)
                    }
                }
            }
        }
    }

    private fun typeTransaction(typeTransactionParams:Int){
        when(typeTransactionParams){
            1 -> {
                requestPulsa = intent.getParcelableExtra("dataPulsa")
                idProduct = requestPulsa.idProduct
                binding.tvTotalPay.text = requestPulsa.totalPrice
                binding.tvPrice.text = requestPulsa.totalPrice
                binding.tvServiceType.text = requestPulsa.typeService
                titleService = "Pulsa"
                binding.tvTitleService.text = titleService
                typeTransaction = 1
            }
            2 -> {
                requestPulsa = intent.getParcelableExtra("dataPulsa")
                idProduct = requestPulsa.idProduct
                binding.tvTotalPay.text = requestPulsa.totalPrice
                binding.tvPrice.text = requestPulsa.totalPrice
                binding.tvServiceType.text = requestPulsa.typeService
                titleService = "Paket Data"
                binding.tvTitleService.text = titleService
                typeTransaction = 1
            }
        }
    }

    private fun move(status:String){
        if (status.contains("Success")){
            when(typeTransaction){
                1 -> {
                    val intent = Intent(applicationContext, PaymentActivity::class.java)
                        .putExtra("dataPulsa", requestPulsa)
                        .putExtra("typeTransaction", 1)
                    startActivity(intent)
                }
                2->{
                    val intent = Intent(applicationContext, PaymentActivity::class.java)
                        .putExtra("dataPulsa", requestPulsa)
                        .putExtra("typeTransaction", 2)
                    startActivity(intent)
                }
            }

        } else {
            Toast.makeText(applicationContext, status, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageCheckout(msg: String) {
        Log.d("checkout", msg)
        move(msg)
    }
}