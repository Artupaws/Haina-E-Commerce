package haina.ecommerce.view.payment

import android.annotation.SuppressLint
import android.app.Dialog
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
import haina.ecommerce.model.DataPaymentMethod

class PaymentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPaymentBinding
    private var popupPaymentMethod: Dialog? = null
    private val listPaymentMethod = arrayListOf(DataPaymentMethod("123","ABC","4000"),
        DataPaymentMethod("345","CBA","4000"),
        DataPaymentMethod("567","BCA","4000"),
        DataPaymentMethod("789","IRB","4000"),)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPayment.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarPayment.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarPayment.title = getString(R.string.pay)
        binding.frameChoosePaymentMethod.setOnClickListener(this)
        dialogPaymentMethod()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.frame_choose_payment_method -> {
                popupPaymentMethod?.show()
            }
        }
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

        adapterPaymentMethod.onItemClick = {bankName:String, serviceFee:String ->
            popupPaymentMethod?.dismiss()
            binding.tvChoosePaymentMethod.visibility = View.GONE
            binding.linearPaymentMethod.visibility = View.VISIBLE
            binding.tvServiceFee.text = serviceFee
            binding.tvNameBank.text = bankName
        }

    }
}