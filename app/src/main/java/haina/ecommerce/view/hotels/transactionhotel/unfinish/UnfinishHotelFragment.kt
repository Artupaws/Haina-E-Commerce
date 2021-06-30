package haina.ecommerce.view.hotels.transactionhotel.unfinish

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterTransactionUnfinish
import haina.ecommerce.countdowntimer.SimpleCountDownTimerKotlin
import haina.ecommerce.databinding.FragmentUnfinishHotelBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.hotels.newHotel.DataBooking
import haina.ecommerce.model.hotels.newHotel.PaidItem
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel
import haina.ecommerce.model.hotels.transactionhotel.UnpaidItem
import haina.ecommerce.view.hotels.transactionhotel.DetailBookingsActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment

class UnfinishHotelFragment : Fragment(), AdapterTransactionUnfinish.ItemAdapterCallback, SimpleCountDownTimerKotlin.OnCountDownListener{

    private lateinit var _binding: FragmentUnfinishHotelBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null
    private var adapterUnfinish:AdapterTransactionUnfinish? = null
    private var countDown: TextView? = null
    private lateinit var countDownTimer:SimpleCountDownTimerKotlin

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUnfinishHotelBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        countDownStart(1,0)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("dataBooking"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when (intent?.action) {
                "dataBooking" -> {
                    val dataTransaction = intent.getParcelableExtra<DataBooking>("bookingHotel")
                    adapterUnfinish =  AdapterTransactionUnfinish(requireActivity(), dataTransaction.unpaid, this@UnfinishHotelFragment)
                    adapterUnfinish!!.notifyDataSetChanged()
                    setListTransaction(dataTransaction.unpaid)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver)
    }

    private fun setListTransaction(data: List<PaidItem?>?) {
        showIsEmpty(data?.size)
        binding.rvUnfinishHotel.apply {
            adapter = adapterUnfinish
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showIsEmpty(listItem:Int?){
        Log.d("item", listItem.toString())
        if (listItem == 0){
            binding.rvUnfinishHotel.visibility = View.GONE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View, data: PaidItem) {
        when (view.id) {
            R.id.btn_copy_number -> {
                copyVirtualAccount(data.payment?.vaNumber!!)
            }
            R.id.cv_click -> {
                val intent = Intent(requireActivity(), DetailBookingsActivity::class.java)
                    .putExtra("data", data)
                startActivity(intent)
            }
            R.id.iv_action_cancel -> {
                val popup = androidx.appcompat.widget.PopupMenu(requireActivity(), binding.rvUnfinishHotel)
                popup.inflate(R.menu.menu_cancel_transaction)
                popup.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.action_cancel_transaction -> {
                            data.payment?.bookingId?.let {
                                (activity as HistoryTransactionHotelActivity).cancelBookingHotel(it)
                            }
                            true
                        }
                        else -> false
                    }
                }
            }
            R.id.btn_how_pay -> {
                val bundle = Bundle()
                bundle.putParcelable("dataTransactionHotel", data)
                bundle.putBoolean("transactionHotel", true)
                childFragmentManager.let {
                    BottomSheetHowToPayment.newInstance(bundle).apply {
                        show(it, "data")
                    }
                }
            }
        }
    }

    private fun copyVirtualAccount(paymentNumber: String) {
        val myClipboard: ClipboardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager;
        val myClip = ClipData.newPlainText("text", paymentNumber)
        myClipboard.setPrimaryClip(myClip)
        Toast.makeText(context, "Virtual Account Copied", Toast.LENGTH_SHORT).show()
    }

    override fun onCountDownActive(time: String) {
        (requireActivity() as Activity).runOnUiThread {
            countDown = requireActivity().findViewById(R.id.tv_notif_countdown)
            countDown?.text = countDownTimer.getSecondsTillCountDown().toString()
           Toast.makeText(requireActivity(), time, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCountDownFinished() {
        countDown?.text = "this transaction has expired"
        Toast.makeText(requireActivity(), "expired", Toast.LENGTH_SHORT).show()
    }

    private fun countDownStart(minuteSession:Int, secondSession:Int){
        countDownTimer = SimpleCountDownTimerKotlin(minuteSession.toLong(), secondSession.toLong(), this)
        countDownTimer.start()
        countDownTimer.runOnBackgroundThread()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.pause()
    }
}