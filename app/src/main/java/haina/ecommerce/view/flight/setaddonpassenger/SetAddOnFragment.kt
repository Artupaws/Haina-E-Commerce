package haina.ecommerce.view.flight.setaddonpassenger

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAddOn
import haina.ecommerce.adapter.flight.AdapterCombinePassengerAndFlight
import haina.ecommerce.databinding.FragmentSetAddOnPassengerBinding
import haina.ecommerce.model.flight.*
import java.util.ArrayList


class SetAddOnFragment : Fragment(), SetAddOnContract, AdapterAddOn.ItemAdapterCallback, View.OnClickListener,AdapterCombinePassengerAndFlight.CallbackInterface{

    private lateinit var _binding: FragmentSetAddOnPassengerBinding
    private val binding get() = _binding
    private var dataPassengerParams = java.util.ArrayList<DataSetPassenger>()
    private var dataFlightParams = java.util.ArrayList<Ticket>()
    private lateinit var presenter: SetAddOnPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var totalPriceAddOn: Int = 0
    private var totalPrice: TextView? = null

    private val STATUS_AVAILABLE = 1
    private val STATUS_BOOKED = 2
    private val STATUS_RESERVED = 3

    private var selectedIds = ""
    private var dataAddonsAll:MutableList<PaxDataAddons> = mutableListOf()
    private val bundle = Bundle()

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

        bundle.putParcelableArrayList("dataFlight", dataFlight)
        bundle.putParcelableArrayList("dataPassenger", dataSetPassenger)
    }

    override fun messageGetAddOn(msg: String) {
        Log.d("getDataAddOn", msg)
    }

    override fun getDataAddOn(data: List<AddOnsItem>?) {
        val adapterCombinePassengerAndFlight = AdapterCombinePassengerAndFlight(
            requireActivity(),
            dataPassengerParams,
            dataFlightParams,
            data,
            this
            )
        binding.rvSetAddOn.apply {
            adapter = adapterCombinePassengerAndFlight
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        binding.tvTotalPriceAddOn.text="Rp. 0"

        binding.buttonAddOn.setOnClickListener {
            presenter.sendDataAddons(RequestSetPassengerAddOn(dataAddonsAll))
        }
    }

    override fun sendDataAddOnSuccess(msg:String) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()

        bundle.putParcelableArrayList("dataAddonsAll", dataAddonsAll as ArrayList<PaxDataAddons>)

        Navigation.findNavController(binding.buttonAddOn).navigate(R.id.action_setAddOnPassengerFragment_to_setBookingFragment, bundle)
    }

    override fun onClickAdapter(view: View, price: Int) {
        when(view.id){
            R.id.cb_addon -> {
                Toast.makeText(requireActivity(), price.toString(), Toast.LENGTH_SHORT).show()
                val priceTotal = price+totalPriceAddOn
                totalPrice?.text = "Total price -on : $priceTotal"
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0!!.tag as Int == STATUS_AVAILABLE) {
            if (selectedIds.contains(p0!!.id.toString() + ",")) {
                selectedIds = selectedIds.replace(p0!!.id.toString() + ",", "")
                p0!!.setBackgroundResource(R.drawable.ic_seats_book)
            } else {
                selectedIds = selectedIds + p0!!.id.toString() + ","
                p0!!.setBackgroundResource(R.drawable.ic_seats_selected)
            }
        } else if (p0!!.tag as Int == STATUS_BOOKED) {
            Toast.makeText(requireActivity(), "Seat " + p0!!.id.toString() + getString(R.string.is_booked), Toast.LENGTH_SHORT)
                .show()
        } else if (p0!!.tag as Int == STATUS_RESERVED) {
            Toast.makeText(
                requireActivity(),
                "Seat " + p0!!.id.toString() + getString(R.string.is_reserved),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    @SuppressLint("SetTextI18n")
    override fun passDataAddonsAll(data: MutableList<PaxDataAddons>) {
        var total=0
        data.forEach{
            total+=it.total!!
        }
        dataAddonsAll=data


        binding.tvTotalPriceAddOn.text= "Rp. $total"


    }


}