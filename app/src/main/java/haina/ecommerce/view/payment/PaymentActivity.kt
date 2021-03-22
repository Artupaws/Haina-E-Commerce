package haina.ecommerce.view.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPaymentMethod
import haina.ecommerce.databinding.ActivityPaymentBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataPaymentMethod
import haina.ecommerce.view.waitingpayment.WaitingPaymentActivity

class PaymentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPaymentBinding
    private var popupPaymentMethod: Dialog? = null
    private val listPaymentMethod = arrayListOf(DataPaymentMethod("123","ABC","4000"),
        DataPaymentMethod("345","CBA","4000"),
        DataPaymentMethod("567","BCA Virtual Account","4000"),
        DataPaymentMethod("789","IRB","4000"),)
    private var numberOrderCustomer:String? = null
    private var price:String? = null
    private var serviceFee:String? = null
    private var discounts:String = "0"
    private var valueTotalPayment:Int? = null
    private var titleService:String? = null
    private val helper:Helper = Helper()
    private var paymentMethod:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPayment.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarPayment.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarPayment.title = getString(R.string.payment)
        binding.frameChoosePaymentMethod.setOnClickListener(this)
        binding.btnPayment.setOnClickListener(this)
        titleService = intent.getStringExtra("titleService")
        dialogPaymentMethod()
        setDetailOrder()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.frame_choose_payment_method -> {
                popupPaymentMethod?.show()
            }
            R.id.btn_payment -> {
                val intent = Intent(applicationContext, WaitingPaymentActivity::class.java)
                        .putExtra("titleService", titleService)
                        .putExtra("totalPayment", valueTotalPayment)
                        .putExtra("paymentMethod", paymentMethod)
                startActivity(intent)
            }
        }
    }

    private fun setDetailOrder(){
        price = intent.getStringExtra("nominal")
        numberOrderCustomer = intent.getStringExtra("numberCustomer")
        binding.tvTotalBill.text = price
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogPaymentMethod(){
        popupPaymentMethod = Dialog(this)
        popupPaymentMethod?.setContentView(R.layout.layout_pop_up_list)
        popupPaymentMethod?.setCancelable(true)
        popupPaymentMethod?.window?.setBackgroundDrawable(getDrawable(android.R.color.transparent))
        val window:Window = popupPaymentMethod?.window!!
        window.setGravity(Gravity.CENTER)
        val adapterPaymentMethod = AdapterPaymentMethod(applicationContext, listPaymentMethod)
        val title = popupPaymentMethod?.findViewById<TextView>(R.id.tv_title)
        val list = popupPaymentMethod?.findViewById<RecyclerView>(R.id.rv_popup)
        val cancel = popupPaymentMethod?.findViewById<TextView>(R.id.tv_action)
        title?.text = "Payment Method"
        cancel?.visibility = View.GONE
        list?.apply {
            adapter = adapterPaymentMethod
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        adapterPaymentMethod.onItemClick = {bankName:String, serviceFeeValue:String ->
            serviceFee = serviceFeeValue
            popupPaymentMethod?.dismiss()
            binding.tvChoosePaymentMethod.visibility = View.GONE
            binding.linearPaymentMethod.visibility = View.VISIBLE
            binding.tvServiceFee.text = helper.convertToFormatMoneyIDRFilter(serviceFee!!)
            binding.tvNameBank.text = bankName
            paymentMethod = bankName
            if (serviceFee?.isNotEmpty()!!){
                setTotalPayment()
                binding.linearTotalPrice.visibility = View.VISIBLE
            }else{
                binding.linearTotalPrice.visibility = View.GONE
            }
        }
    }

    private fun setTotalPayment(){
        val totalPayment = (helper.changeMoneyToValue(price!!).toInt()+helper.changeMoneyToValue(binding.tvServiceFee.text.toString()).toInt()+discounts.toInt())
        binding.tvTotalMustPay.text = totalPayment.toString()
        valueTotalPayment = totalPayment
    }
}