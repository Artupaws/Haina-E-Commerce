package haina.ecommerce.view.flight.setaddonpassenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterCombinePassengerAndFlight
import haina.ecommerce.databinding.FragmentSetAddOnPassengerBinding
import haina.ecommerce.model.flight.DataSetPassenger
import haina.ecommerce.model.flight.Ticket

class SetAddOnPassengerFragment : Fragment(), AdapterCombinePassengerAndFlight.ItemAdapterCallback {

    private lateinit var _binding:FragmentSetAddOnPassengerBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSetAddOnPassengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbarSetAddOn.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val dataSetPassenger = arguments?.getParcelableArrayList<DataSetPassenger>("dataSetPassenger")
        Toast.makeText(requireActivity(), dataSetPassenger?.size.toString(), Toast.LENGTH_SHORT).show()
        val dataFlight = arguments?.getParcelableArrayList<Ticket>("dataFlight")
        Toast.makeText(requireActivity(), dataFlight?.size.toString(), Toast.LENGTH_SHORT).show()
        val adapterCombinePassengerAndFlight = AdapterCombinePassengerAndFlight(requireActivity(), dataSetPassenger!!, dataFlight!!, this)
        binding.rvSetAddOn.apply {
            adapter = adapterCombinePassengerAndFlight
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

    }



}