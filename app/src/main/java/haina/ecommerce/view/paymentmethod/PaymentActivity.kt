package haina.ecommerce.view.paymentmethod

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPaymentMethod
import haina.ecommerce.databinding.ActivityPaymentBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.paymentmethod.DataPaymentMethod
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity

class PaymentActivity : AppCompatActivity(), View.OnClickListener, PaymentContract {

    private lateinit var binding: ActivityPaymentBinding
    private var popupPaymentMethod: Dialog? = null
    private var numberOrderCustomer: String? = null
    private var price: String? = null
    private var serviceFee: Int? = 0
    private var discounts: String = "0"
    private var valueTotalPayment: Int? = null
    private var titleService: String? = null
    private val helper: Helper = Helper()
    private var paymentMethod: String = ""
    private lateinit var presenter: PaymentPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idPaymentMethod: Int? = null
    private var idProduct: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PaymentPresenter(this, this)
        presenter.getPaymentMethod()
        broadcaster = LocalBroadcastManager.getInstance(this)
        binding.toolbarPayment.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarPayment.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarPayment.title = getString(R.string.payment)
        binding.frameChoosePaymentMethod.setOnClickListener(this)
        binding.btnPayment.setOnClickListener(this)
        setDetailOrder()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.frame_choose_payment_method -> {
                popupPaymentMethod?.show()
            }
            R.id.btn_payment -> {
                presenter.createTransaction(numberOrderCustomer!!, idProduct!!, idPaymentMethod!!)
//                val intent = Intent(applicationContext, HistoryTransactionActivity::class.java)
//                        .putExtra("titleService", titleService)
//                        .putExtra("totalPayment", valueTotalPayment)
//                        .putExtra("paymentMethod", paymentMethod)
//                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("paymentMethod"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "paymentMethod" -> {
                    val id = intent.getIntExtra("idPayment", 0)
                    val bank = intent.getStringExtra("bankName")
                    idPaymentMethod = id
                    setTotalPayment(idPaymentMethod)
                    binding.tvChoosePaymentMethod.visibility = View.GONE
                    binding.linearPaymentMethod.visibility = View.VISIBLE
                    binding.tvNameBank.text = bank
                }
            }
        }
    }

    private fun setDetailOrder() {
        price = intent.getStringExtra("nominal")
        numberOrderCustomer = intent.getStringExtra("numberCustomer")
        binding.tvTotalBill.text = price
        titleService = intent.getStringExtra("titleService")
        idProduct = intent.getIntExtra("idProduct", 0)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogPaymentMethod(list: List<DataPaymentMethod?>?) {
        popupPaymentMethod = Dialog(this)
        popupPaymentMethod?.setContentView(R.layout.layout_pop_up_list)
        popupPaymentMethod?.setCancelable(true)
        popupPaymentMethod?.window?.setBackgroundDrawable(getDrawable(android.R.color.transparent))
        val window: Window = popupPaymentMethod?.window!!
        window.setGravity(Gravity.CENTER)
        val title = popupPaymentMethod?.findViewById<TextView>(R.id.tv_title)
        val rvPaymentMethod = popupPaymentMethod?.findViewById<RecyclerView>(R.id.rv_popup)
        val cancel = popupPaymentMethod?.findViewById<TextView>(R.id.tv_action)
        title?.text = "Payment Method"
        cancel?.visibility = View.GONE

        rvPaymentMethod?.apply {
            adapter = AdapterPaymentMethod(applicationContext, list)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
//        adapterPaymentMethod.onItemClick = {bankName:String, serviceFeeValue:String ->
//            serviceFee = serviceFeeValue
//            popupPaymentMethod?.dismiss()
//            binding.tvChoosePaymentMethod.visibility = View.GONE
//            binding.linearPaymentMethod.visibility = View.VISIBLE
//            binding.tvServiceFee.text = helper.convertToFormatMoneyIDRFilter(serviceFee!!)
//            binding.tvNameBank.text = bankName
//            paymentMethod = bankName
//            if (serviceFee?.isNotEmpty()!!){
//                setTotalPayment()
//                binding.linearTotalPrice.visibility = View.VISIBLE
//            }else{
//                binding.linearTotalPrice.visibility = View.GONE
//            }
//        }
    }

    private fun setTotalPayment(idPaymentMethod: Int?) {
        if (idPaymentMethod != 0) {
            popupPaymentMethod?.dismiss()
            binding.linearTotalPrice.visibility = View.VISIBLE
//            val totalPayment = (helper.changeMoneyToValue(price!!).toInt() + serviceFee!! + discounts.toInt())
            binding.tvTotalMustPay.text = price
            valueTotalPayment = helper.changeFormatMoneyToValueFilter(price!!)?.toInt()
        } else {
            binding.linearTotalPrice.visibility = View.GONE
        }
    }

    override fun messageGetPaymentMethod(msg: String) {
        Log.d("paymentMethod", msg)
    }

    override fun messageCreateTransaction(msg: String) {
        Log.d("transaction", msg)
        if (msg.contains("Success")){
            val intent = Intent(applicationContext, HistoryTransactionActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getDataPaymentMethod(data: List<DataPaymentMethod?>?) {
        dialogPaymentMethod(data)
    }
}