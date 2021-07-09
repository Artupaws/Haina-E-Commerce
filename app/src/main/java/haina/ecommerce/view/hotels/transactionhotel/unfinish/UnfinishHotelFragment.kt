package haina.ecommerce.view.hotels.transactionhotel.unfinish

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterTransactionUnfinish
import haina.ecommerce.broadcastcountdown.BroadcastService
import haina.ecommerce.databinding.FragmentUnfinishHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataBooking
import haina.ecommerce.model.hotels.newHotel.PaidItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.hotels.transactionhotel.detailbook.DetailBookingsActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment


class UnfinishHotelFragment : Fragment(), AdapterTransactionUnfinish.ItemAdapterCallback {
    var TAG = "Main"
    private lateinit var _binding: FragmentUnfinishHotelBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null
    private var adapterUnfinish: AdapterTransactionUnfinish? = null
    var countDown: TextView? = null
    private var minutes: Long = 1
    private var seconds: Long = 0
    private lateinit var sharedPref: SharedPreferenceHelper
    private var dataTransaction: DataBooking? = null
    private var statusCountDown:String =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUnfinishHotelBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        sharedPref = SharedPreferenceHelper(requireActivity())
        countDown = requireActivity().findViewById(R.id.tv_notif_countdown)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val dataBooking = dataTransaction?.unpaid?.size
        countDown = requireActivity().findViewById(R.id.tv_notif_countdown)
        statusCountDown = sharedPref.getValueString(Constants.CURRENT_TIME_SESSION_PAYMENT).toString()
        if (statusCountDown == "finish") {
            countDown?.text = "expired"
        }
//        Log.d("dataBookingTop", dataBooking.toString())

        Log.i(TAG, "Started Service")
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(broadcastReceiver, IntentFilter(BroadcastService().COUNTDOWN_BR))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(broadcastReceiver)
        Log.i(TAG, "Unregistered broadcast receiver")
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("dataBooking"))
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Update GUI
            updateGUI(intent)
        }
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when (intent?.action) {
                "dataBooking" -> {
                    val statusCountDown = sharedPref.getValueString(Constants.CURRENT_TIME_SESSION_PAYMENT)
                    Log.d("statusCountDown", statusCountDown.toString())
                    val intentCountdown = Intent(requireActivity(), BroadcastService::class.java)
                    dataTransaction = intent.getParcelableExtra<DataBooking>("bookingHotel")
                    adapterUnfinish = AdapterTransactionUnfinish(requireActivity(), dataTransaction?.unpaid, this@UnfinishHotelFragment)
                    adapterUnfinish!!.notifyDataSetChanged()
                    setListTransaction(dataTransaction!!.unpaid)
                    if (dataTransaction!!.unpaid?.size != 0 && statusCountDown != "finish"){
                        requireActivity().startService(intentCountdown)
                    } else {
                        countDown = requireActivity().findViewById(R.id.tv_notif_countdown)
                        countDown?.text = "expired"
                        Log.d("dataTransactionNull", dataTransaction!!.unpaid?.size.toString())
                        requireActivity().stopService(intentCountdown)
                    }
                    Log.d("dataBooking", dataTransaction!!.unpaid?.size.toString())
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
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun updateGUI(intent: Intent) {
        if (intent.extras != null) {
            val millisUntilFinished = intent.getLongExtra("countdown", 1000)
            Log.i(TAG, "Countdown seconds remaining:" + millisUntilFinished / 1000)
            countDown = requireActivity().findViewById(R.id.tv_notif_countdown)
            countDown?.text = "Do payment before : ${millisUntilFinished / 1000} seconds"
            if (countDown?.text == "Do payment before : 0 seconds"){
                countDown?.text = "expired"
                val intentCancelBooking = Intent("cancelBooking")
                broadcaster?.sendBroadcast(intentCancelBooking)
                sharedPref.save(Constants.CURRENT_TIME_SESSION_PAYMENT, "expired")
            }
            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(requireActivity().packageName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putLong("time", millisUntilFinished).apply()
        }
    }

    private fun showIsEmpty(listItem: Int?) {
        Log.d("item", listItem.toString())
        if (listItem == 0) {
            binding.rvUnfinishHotel.visibility = View.GONE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View, data: PaidItem) {
        when (view.id) {
            R.id.btn_copy_number -> {
                sharedPref.removeValue(Constants.CURRENT_TIME_SESSION_PAYMENT)
                copyVirtualAccount(data.payment?.vaNumber!!)
            }
            R.id.cv_click -> {
                val intent = Intent(requireActivity(), DetailBookingsActivity::class.java)
                    .putExtra("data", data)
                startActivity(intent)
            }
            R.id.iv_action_cancel -> {
                val popup =
                    androidx.appcompat.widget.PopupMenu(requireActivity(), binding.rvUnfinishHotel)
                popup.inflate(R.menu.menu_cancel_transaction)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
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

}