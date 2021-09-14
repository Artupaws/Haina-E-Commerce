package haina.ecommerce.view.hotels.transactionhotel.cancel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterTransactionFinish
import haina.ecommerce.databinding.FragmentCancelHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataBooking
import haina.ecommerce.model.hotels.newHotel.PaidItem

class CancelHotelFragment : Fragment(), AdapterTransactionFinish.ItemAdapterCallback {

    private lateinit var _binding:FragmentCancelHotelBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCancelHotelBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("dataBooking"))
    }

    private val mMessageReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            when(intent?.action){
                "dataBooking" -> {
                    val dataTransactionFinish = intent.getParcelableExtra<DataBooking>("bookingHotel")
                    setListTransaction(dataTransactionFinish?.cancelled)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver)
    }

    private fun setListTransaction(data:List<haina.ecommerce.model.hotels.newHotel.PaidItem?>?){
        showIsEmpty(data?.size)
        binding.rvBooking.apply {
            adapter = AdapterTransactionFinish(requireActivity(), data, this@CancelHotelFragment,"cancel")
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showIsEmpty(listItem:Int?){
        Log.d("item", listItem.toString())
        if (listItem == 0){
            binding.rvBooking.visibility = View.GONE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        } else {
            binding.rvBooking.visibility = View.VISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.GONE
        }
    }

    override fun onClick(view: View, data: PaidItem) {
        TODO("Not yet implemented")
    }


}