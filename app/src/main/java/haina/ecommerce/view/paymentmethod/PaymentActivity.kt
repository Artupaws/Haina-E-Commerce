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
import haina.ecommerce.helper.Helper.changeFormatMoneyToValue
import haina.ecommerce.helper.Helper.convertToFormatMoneyIDRFilter
import haina.ecommerce.model.bill.RequestBill
import haina.ecommerce.model.hotels.Requesthotel
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelDarma
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelToDarma
import haina.ecommerce.model.paymentmethod.DataPaymentMethod
import haina.ecommerce.model.pulsaanddata.RequestPulsa
import haina.ecommerce.model.vacancy.RequestCreateVacancy
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity
import haina.ecommerce.view.posting.newvacancy.VacancyContract
import haina.ecommerce.view.property.FinishPropertyActivity
import timber.log.Timber
import java.util.ArrayList

class PaymentActivity : AppCompatActivity(), View.OnClickListener,
    PaymentContract.View{

    private lateinit var binding: ActivityPaymentBinding
    private var popupPaymentMethod: Dialog? = null
    private var price: String? = null
    private var valueTotalPayment: Int? = null
    private val helper: Helper = Helper
    private lateinit var presenter: PaymentPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idPaymentMethod: Int? = null
    private var typeTransactionParams:Int = 0
    private var dataPulsa:RequestPulsa? = null
    private var requestBill:RequestBill? = null
    private var dataBooking:Requesthotel? = null
    private var dataBookingHotelDarma: RequestBookingHotelDarma? = null
    private var requestToDarma : RequestBookingHotelToDarma? = null
    private var progressDialog:Dialog? = null
    private var list = ArrayList<String>()
    private var stringRequest :String = ""
    private var smokingRoomValue:Int = 0

    private var requestCreateVacancy:RequestCreateVacancy? = null
    private var priceVacancyAds:Int = 0
    private var packageNameVacancyAds:String? = null


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
        typeTransactionParams = intent.getIntExtra("typeTransaction", 0)
        //DarmaWisata
        dataBookingHotelDarma = intent.getParcelableExtra("dataBooking")

        setDetailOrder(typeTransactionParams)
        dialogLoading()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.frame_choose_payment_method -> {
                popupPaymentMethod?.show()
            }
            R.id.btn_payment -> {
                when(typeTransactionParams){
                    1 -> { presenter.createTransaction(dataPulsa?.phoneNumber!!, dataPulsa?.productCode!!,
                    idPaymentMethod!!, dataPulsa!!.idInquiry)
                    }
                    2 -> {presenter.createBillTransaction(requestBill?.productCode!!, requestBill?.amount!!, requestBill?.customerNumber!!, idPaymentMethod!!, requestBill?.inquiry)
                    }
                    3 -> { presenter.createBookingHotel(dataBooking?.hotelId!!, dataBooking?.roomId!!, dataBooking?.checkIn!!, dataBooking?.checkOut!!, dataBooking?.totalGuest!!,
                   helper.changeFormatMoneyToValueFilter(dataBooking?.totalPrice)?.toInt()!!, idPaymentMethod!!)
                    }
                    4 -> {
                        presenter.createBookingHotelDarma(requestToDarma!!)
                    }
                    5 -> {
                        presenter.createVacancyPaid(this.requestCreateVacancy!!.position, this.requestCreateVacancy!!.idCompany, this.requestCreateVacancy!!.idSpecialist, this.requestCreateVacancy!!.level, this.requestCreateVacancy!!.type,
                            this.requestCreateVacancy!!.description, this.requestCreateVacancy!!.experience, this.requestCreateVacancy!!.idEdu!!, this.requestCreateVacancy!!.minSalary.toInt(), this.requestCreateVacancy!!.maxSalary.toInt(), this.requestCreateVacancy!!.salaryDisplay,
                            this.requestCreateVacancy!!.address, this.requestCreateVacancy!!.idCity, this.requestCreateVacancy!!.packageAds!!, idPaymentMethod!!, this.requestCreateVacancy!!.skill!!)
                    }
                    6 -> {

                    }
                }
            }
        }
    }

    private fun setRequestSraAndSmokeRoom(idPaymentMethod: Int?){
//        for (data in dataBookingHotelDarma?.special_request_array_complete!!){
//            list.add(data.iD!!)
//        }
//        val separator = ", "
//        stringRequest = list.joinToString(separator)
//        smokingRoomValue = if (dataBookingHotelDarma?.smokingRoom == true){
//            1
//        } else {
//            0
//        }
        stringRequest = dataBookingHotelDarma?.specialRequest!!
        requestToDarma= RequestBookingHotelToDarma(smokingRoomValue, dataBookingHotelDarma!!.phone,
            stringRequest, idPaymentMethod, dataBookingHotelDarma!!.paxes, dataBookingHotelDarma!!.email, dataBookingHotelDarma!!.price)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(getDrawable(android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
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
                    when(typeTransactionParams){
                        4 -> {
                            setRequestSraAndSmokeRoom(idPaymentMethod)
                        }
                    }
                }
            }
        }
    }

    private fun setDetailOrder(typeTransaction: Int) {
        when(typeTransaction){
            1 -> {
                dataPulsa = intent.getParcelableExtra("dataPulsa")
                Log.d("id_inquiry", dataPulsa?.idInquiry.toString())
                binding.tvTotalBill.text = dataPulsa?.totalPrice
                price = dataPulsa?.totalPrice
            }
            2 -> {
                requestBill = intent?.getParcelableExtra("request")
                Log.d("dataBillPayment", requestBill.toString())
                price = helper.convertToFormatMoneyIDRFilter(requestBill?.amount)
                binding.tvTotalBill.text = price
//                val totalPrice = requestBill?.adminFee?.toInt()?.plus(requestBill?.amount?.toInt()!!)
//                price = totalPrice.toString()
            }
            3 -> {
                dataBooking = intent.getParcelableExtra("dataBooking")
                binding.tvTotalBill.text = dataBooking?.totalPrice
                price = dataBooking?.totalPrice
            }

            4 -> {
                binding.tvTotalBill.text = dataBookingHotelDarma?.price
                price = dataBookingHotelDarma?.price
            }

            5 -> {
                requestCreateVacancy = intent.getParcelableExtra("requestCreateVacancy")
                packageNameVacancyAds = intent.getStringExtra("packageName")
                priceVacancyAds = intent.getIntExtra("priceVacancy", 0)
                binding.tvTotalBill.text = convertToFormatMoneyIDRFilter(priceVacancyAds.toString())
                price = changeFormatMoneyToValue(priceVacancyAds.toString())
            }

            6 -> {
                binding.tvTotalBill.text = dataBookingHotelDarma?.price
                price = dataBookingHotelDarma?.price
            }
        }
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
    }

    private fun setTotalPayment(idPaymentMethod: Int?) {
        if (idPaymentMethod != 0) {
            popupPaymentMethod?.dismiss()
            binding.linearTotalPrice.visibility = View.VISIBLE
             binding.tvTotalMustPay.text = price
//            valueTotalPayment = helper.changeFormatMoneyToValueFilter(price!!)?.toInt()
        } else {
            binding.linearTotalPrice.visibility = View.GONE
        }
    }

    override fun messageGetPaymentMethod(msg: String) {
        Log.d("paymentMethod", msg)
    }

    override fun messageCreateTransaction(msg: String) {
        Log.d("transactionPulsa", msg)
        if (msg.contains("Success")){
            val intent = Intent(applicationContext, HistoryTransactionActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageCreateBillTransaction(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
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

    override fun messageBookingHotel(msg: String) {
        Log.d("transactionHotel", msg)
        if (msg.contains("Success")){
            val intent = Intent(applicationContext, HistoryTransactionHotelActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageBookingFlight(msg: String) {
        if (msg.contains("Success")){
//            val intent = Intent(applicationContext, HistoryTransactionHotelActivity::class.java)
//            startActivity(intent)
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageCreateVacancy(msg: String) {
        Timber.d(msg)
        if (msg.contains("Paid vacancy created successfully!")){
            startActivity(Intent(applicationContext, FinishPropertyActivity::class.java).putExtra("finish", "vacancy"))
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}