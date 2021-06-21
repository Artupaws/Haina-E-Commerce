package haina.ecommerce.view.flight.setaddonpassenger

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterCombinePassengerAndFlight
import haina.ecommerce.databinding.FragmentSetAddOnPassengerBinding
import haina.ecommerce.model.flight.DataAddOn
import haina.ecommerce.model.flight.DataSetPassenger
import haina.ecommerce.model.flight.Ticket
import haina.ecommerce.roomdatapassenger.DataPassenger

class SetAddOnPassengerFragment : Fragment(), AdapterCombinePassengerAndFlight.ItemAdapterCallback, SetAddOnContract {

    private lateinit var _binding:FragmentSetAddOnPassengerBinding
    private val binding get() = _binding
    private var dataPassengerParams = java.util.ArrayList<DataSetPassenger>()
    private var dataFlightParams = java.util.ArrayList<Ticket>()
    private lateinit var presenter: SetAddOnPresenter
    private var broadcaster:LocalBroadcastManager? = null
    private var popupShowChooseSeat: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSetAddOnPassengerBinding.inflate(inflater, container, false)
        presenter = SetAddOnPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getDataAddOn()
        binding.toolbarSetAddOn.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val dataSetPassenger = arguments?.getParcelableArrayList<DataSetPassenger>("dataSetPassenger")
        dataPassengerParams = dataSetPassenger!!
        val dataFlight = arguments?.getParcelableArrayList<Ticket>("dataFlight")
        dataFlightParams = dataFlight!!
        popupDialogChooseSeat()
    }

    private val mMessageReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            when(intent?.action){
                "chooseSeat" -> {
                    popupShowChooseSeat?.show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("chooseSeat"))
    }

    override fun onStop() {
        super.onStop()
        broadcaster?.unregisterReceiver(mMessageReceiver)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogChooseSeat() {
        popupShowChooseSeat = Dialog(requireActivity())
        popupShowChooseSeat?.setContentView(R.layout.layout_pop_up_choose_seat)
        popupShowChooseSeat?.setCancelable(true)
        popupShowChooseSeat?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupShowChooseSeat?.window!!
        window.setGravity(Gravity.CENTER)
        val title = popupShowChooseSeat?.findViewById<TextView>(R.id.tv_title)
        val btnSave = popupShowChooseSeat?.findViewById<Button>(R.id.btn_save)
        val scrollSeat = popupShowChooseSeat?.findViewById<NestedScrollView>(R.id.scroll_seat)


    }

    override fun messageGetAddOn(msg: String) {
        Log.d("getDataAddOn", msg)
    }

    override fun getDataAddOn(data: DataAddOn?) {
        val adapterCombinePassengerAndFlight = AdapterCombinePassengerAndFlight(requireActivity(), dataPassengerParams, dataFlightParams, data!!,this)
        binding.rvSetAddOn.apply {
            adapter = adapterCombinePassengerAndFlight
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }
}