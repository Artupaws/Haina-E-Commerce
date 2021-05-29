package haina.ecommerce.view.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityCheckoutBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.convertLongtoDate
import haina.ecommerce.model.Login
import haina.ecommerce.model.bill.DataBill
import haina.ecommerce.model.bill.DataInquiry
import haina.ecommerce.model.bill.DataNoInquiry
import haina.ecommerce.model.bill.RequestBill
import haina.ecommerce.model.pulsaanddata.RequestPulsa
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.login.LoginActivity
import haina.ecommerce.view.paymentmethod.PaymentActivity

class CheckoutActivity : AppCompatActivity(), View.OnClickListener, CheckoutContract {

    private lateinit var binding: ActivityCheckoutBinding
    private var titleService:String? = null
    private var customerNumber:String? = null
    private var productCode:String? = null
    private val helper:Helper = Helper
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var presenter: CheckoutPresenter
    private var typeTransaction:Int = 0
    private var requestPulsa:RequestPulsa? = null
    private var requestBill:RequestBill? = null
    private var billAmount:String = ""
    private var adminFee:String = ""
    private var totalAmount:String = ""
    private var dataBill: DataInquiry? = null
    private var backTo:Int = 0

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
        binding.btnLogin.setOnClickListener(this)

        customerNumber = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER_PULSA)
        binding.tvNumber.text = customerNumber

        val typeTransactionParams = intent.getIntExtra("typeTransaction", 0)
        typeTransaction(typeTransactionParams)
        statusLogin(sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_payment -> {
                when(typeTransaction){
                    1 -> {
                        requestPulsa?.productCode?.let { requestPulsa?.phoneNumber?.let { it1 -> presenter.checkout(it1, it) } }
                    }
                    2 -> {
                        val requestBillFromCheckout = RequestBill(requestBill?.productCode, billAmount, adminFee, this.requestBill?.customerNumber, null, null)
                        val intent = Intent(applicationContext, PaymentActivity::class.java)
                            .putExtra("request", requestBillFromCheckout)
                            .putExtra("typeTransaction", typeTransaction)
                        startActivity(intent)
                    }
                }
            }
            R.id.btn_login -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                    .putExtra("loginMethod", 0)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        statusLogin(sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN))
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            backTo = 1
        }
    }

    private fun typeTransaction(typeTransactionParams:Int){
        when(typeTransactionParams){
            1 -> {
                requestPulsa = intent.getParcelableExtra("dataPulsa")
                productCode = requestPulsa?.productCode
                binding.tvTotalPay.text = requestPulsa?.totalPrice
                binding.tvPrice.text = requestPulsa?.totalPrice
                binding.tvServiceType.text = requestPulsa?.typeService
                titleService = "Pulsa"
                binding.tvTitleService.text = titleService
                typeTransaction = 1
            }
            2 -> {
                dataBill = intent?.getParcelableExtra("dataBill")
                billAmount = dataBill?.billAmount!!
                adminFee = dataBill?.adminFee!!
                Log.d("billAmount", billAmount)
                requestBill = intent?.getParcelableExtra("request")
                Log.d("dataBillCheckout", requestBill.toString())
                if (requestBill?.inquiry == 1){
                    dataBill?.let { setDetailBillToView(it, sharedPref.getValueString(Constants.LANGUAGE_APP)) }
                }
                if (requestBill?.inquiry == 0){
                    dataBill?.let { setDetailNoInquiry(it, sharedPref.getValueString(Constants.LANGUAGE_APP), requestBill!!) }
                }
                typeTransaction = 2
            }
            3 -> {
                binding.linearDataProductTopup.visibility = View.GONE
                binding.includeDataProductBill.linearDataProductBill.visibility = View.VISIBLE
            }
        }
    }

    private fun statusLogin(status:Boolean){
        when(status){
            true -> {
                binding.btnLogin.visibility = View.GONE
                binding.btnPayment.visibility = View.VISIBLE
            }
            else -> {
                binding.btnLogin.visibility = View.VISIBLE
                binding.btnPayment.visibility = View.GONE
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
//                2->{
//                    val intent = Intent(applicationContext, PaymentActivity::class.java)
//                        .putExtra("request", requestBill)
//                        .putExtra("typeTransaction", 2)
//                    startActivity(intent)
//                }
            }
        } else {
            Toast.makeText(applicationContext, status, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        when(backTo) {
            1 -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            0 -> {
                super.onBackPressed()
            }
        }
    }

    private fun setDetailBillToView(data:DataInquiry, codeLanguage:String?){
        binding.linearDataProductTopup.visibility = View.GONE
        binding.includeDataProductBill.linearDataProductBill.visibility = View.VISIBLE
        binding.includeDataProductBill.tvCustomerNumber.text = data.billData?.customerId
        binding.includeDataProductBill.tvNameCustomer.text = data.billData?.customerName
        binding.includeDataProductBill.tvBill.text = data.billAmount
        binding.includeDataProductBill.tvBillDate.text = data.billData?.billDate
        binding.includeDataProductBill.tvAdminFee.text = "0"
        binding.tvProductName.text = data.product
        binding.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(data.billAmount.toString())
        val icon = HtmlCompat.fromHtml("${data.iconCode}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.faIcon.text = icon
        when(codeLanguage){
            "en" -> {
                binding.tvTitleService.text = data.category
            }
            "zh" -> {
                binding.tvTitleService.text = data.categoryZh
            }
        }
    }

    private fun setDetailNoInquiry(data:DataInquiry, codeLanguage:String?, dataRequest:RequestBill){
        binding.linearDataProductTopup.visibility = View.GONE
        binding.includeDataProductBill.linearDataProductBill.visibility = View.VISIBLE
        binding.includeDataProductBill.tvCustomerNumber.text = data.billData?.customerId
        binding.includeDataProductBill.linearNameCustomer.visibility= View.GONE
        binding.includeDataProductBill.linearBill.visibility= View.GONE
        binding.includeDataProductBill.tvBillDate.text = data.billData?.billDate
        binding.includeDataProductBill.linearAdminFee.visibility= View.GONE
        binding.tvProductName.text = data.product
        binding.tvTotalPay.text = helper.convertToFormatMoneyIDRFilter(dataRequest.amount.toString())
        when(codeLanguage){
            "en" -> {
                binding.tvTitleService.text = data.category
            }
            "zh" -> {
                binding.tvTitleService.text = data.categoryZh
            }
        }
    }

    override fun messageCheckout(msg: String) {
        Log.d("checkout", msg)
        move(msg)
    }
}