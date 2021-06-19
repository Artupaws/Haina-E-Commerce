package haina.ecommerce.view.flight.setaddonpassenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
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