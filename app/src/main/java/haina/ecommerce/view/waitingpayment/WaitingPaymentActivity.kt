package haina.ecommerce.view.waitingpayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import haina.ecommerce.databinding.ActivityWaitingPaymentBinding

class WaitingPaymentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityWaitingPaymentBinding
    private var titleService:String = ""
    private var totalPayment:String = ""
    private var paymentMethod:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleService = intent.getStringExtra("titleService")
        paymentMethod = intent.getStringExtra("paymentMethod")
        totalPayment = intent.getStringExtra("totalPayment")
        setLayoutPayment(paymentMethod)
        binding.tvTitleService.text = titleService
    }

    private fun setLayoutPayment(paymentMethod:String){
        if (paymentMethod.contains("Virtual Account")){
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.VISIBLE
            binding.includeBankTransfer.linearBankTransfer.visibility = View.GONE
            binding.includeVirtualAccount.tvPaymentMethod.text = paymentMethod
            binding.includeVirtualAccount.tvTotalPay.text = totalPayment
        } else {
            binding.includeVirtualAccount.linearVirtualAccount.visibility = View.GONE
            binding.includeBankTransfer.linearBankTransfer.visibility = View.VISIBLE
//            binding.includeBankTransfer.tvPaymentMethod.text = paymentMethod
            binding.includeBankTransfer.tvTotalPay.text = totalPayment
        }
    }

    override fun onClick(v: View?) {

    }
}