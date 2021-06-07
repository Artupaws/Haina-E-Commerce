package haina.ecommerce.view.hotels.transactionhotel.unfinish

import android.content.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterTransactionUnfinish
import haina.ecommerce.databinding.FragmentUnfinishHotelBinding
import haina.ecommerce.databinding.ListItemUnfinishTransactionBinding
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel
import haina.ecommerce.model.hotels.transactionhotel.UnpaidItem
import haina.ecommerce.view.hotels.transactionhotel.DetailBookingsActivity

class UnfinishHotelFragment : Fragment(), AdapterTransactionUnfinish.ItemAdapterCallback {

    private lateinit var _binding: FragmentUnfinishHotelBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUnfinishHotelBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(mMessageReceiver, IntentFilter("dataTransactionHotel"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when (intent?.action) {
                "dataTransactionHotel" -> {
                    val dataTransaction =
                        intent.getParcelableExtra<DataTransactionHotel>("transactionHotel")
                    setListTransaction(dataTransaction.unpaid)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver)
    }

    private fun setListTransaction(data: List<UnpaidItem?>?) {
        showIsEmpty(data?.size)
        binding.rvUnfinishHotel.apply {
            adapter =
                AdapterTransactionUnfinish(requireActivity(), data, this@UnfinishHotelFragment)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showIsEmpty(listItem:Int?){
        if (listItem == 0){
            binding.rvUnfinishHotel.visibility = View.GONE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View, data: UnpaidItem) {
        when (view.id) {
            R.id.btn_copy_number -> {
                copyVirtualAccount(data.payment?.vaNumber!!)
            }

            R.id.cv_click -> {
                val intent = Intent(requireActivity(), DetailBookingsActivity::class.java)
                    .putExtra("data", data)
                startActivity(intent)
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