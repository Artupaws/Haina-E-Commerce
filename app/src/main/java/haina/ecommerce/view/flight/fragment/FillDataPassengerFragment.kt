package haina.ecommerce.view.flight.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterDataPassenger
import haina.ecommerce.adapter.flight.AdapterListTicket
import haina.ecommerce.databinding.FragmentFillDataPassengerBinding
import haina.ecommerce.databinding.ListItemDataPassengerBinding
import haina.ecommerce.model.flight.AirlinesFirst
import haina.ecommerce.model.flight.Request
import haina.ecommerce.model.flight.Ticket
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.roomdatapassenger.DataPassenger
import haina.ecommerce.roomdatapassenger.PassengerDao
import haina.ecommerce.roomdatapassenger.RoomDataPassenger
import haina.ecommerce.util.Constants
import haina.ecommerce.view.paymentmethod.PaymentActivity

class FillDataPassengerFragment : Fragment(), View.OnClickListener,
    AdapterDataPassenger.ItemAdapterCallback, AdapterListTicket.ItemAdapterCallback {

    private lateinit var _binding: FragmentFillDataPassengerBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var listDataPassenger: ArrayList<DataPassenger>
    private lateinit var data: Request
    private lateinit var database: RoomDataPassenger
    private lateinit var dao: PassengerDao
    private lateinit var listTicket:ArrayList<AirlinesFirst>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFillDataPassengerBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireActivity())
        database = RoomDataPassenger.getDatabase(requireActivity())
        dao = database.getDataPassengerDao()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnAddDataPassenger.setOnClickListener(this)
        binding.btnContinuePayment.setOnClickListener(this)
        binding.toolbarFillDataPassenger.setNavigationOnClickListener {
            findNavController().navigateUp()

        }
        data = arguments?.getParcelable<Request>("data")!!
        setDataOrderer()
        getListDataPassengerDao(database, dao)
        setlistTicket()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_data_passenger -> {
                Navigation.findNavController(binding.btnAddDataPassenger)
                    .navigate(R.id.action_fillDataPassengerFragment_to_detailFillDataPassengerFragment)
            }
            R.id.btn_continue_payment -> {
                setStateButtonContinue(data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getListDataPassengerDao(database, dao)
        setStateButtonContinue(data)
    }

    private fun setDataOrderer() {
        binding.tvNameOrderer.text = sharedPref.getValueString(Constants.PREF_FULLNAME)
        binding.tvEmail.text = sharedPref.getValueString(Constants.PREF_EMAIL)
        binding.tvPhone.text = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)
    }

    private fun setStateButtonContinue(dataParams: Request) {
        if (listDataPassenger.size < dataParams.totalPassenger) {
            Toast.makeText(requireActivity(), "Please fill in the passenger data according to the number", Toast.LENGTH_SHORT).show()
        } else if (listDataPassenger.size == dataParams.totalPassenger) {
          binding.btnAddDataPassenger.visibility = View.GONE
            binding.btnContinuePayment.setOnClickListener {
                moveToPayment()
            }
        }
    }

    private fun getListDataPassengerDao(database: RoomDataPassenger, dao: PassengerDao) {
        listDataPassenger = arrayListOf<DataPassenger>()
        listDataPassenger.addAll(dao.getAll())
        setupListDataPassenger(listDataPassenger)
    }

    private fun setupListDataPassenger(listParams: ArrayList<DataPassenger>) {
        listDataPassenger = listParams
        binding.rvDataPassenger.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter =
                AdapterDataPassenger(requireActivity(), listParams, this@FillDataPassengerFragment)
        }
    }

    override fun onClick(view: View, data: DataPassenger) {
        when(view.id){
            R.id.tv_action_delete -> {
                deleteCart(DataPassenger(data.id, data.fullname, data.birthdate, data.idcard))
                getListDataPassengerDao(database, dao)
            }

            R.id.tv_action_edit -> {
                Toast.makeText(requireActivity(), data.fullname, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteCart(datapassenger: DataPassenger){
        dao.delete(datapassenger)
    }

    private fun moveToPayment(){
        val intentPayment = Intent(requireActivity(), PaymentActivity::class.java)
        startActivity(intentPayment)
    }

    private fun setlistTicket(){
        val ticket = mutableListOf<Ticket>()
        if (data.airlinesSecond != null){
            ticket.addAll(listOf(Ticket(data.airlinesFirst?.nameAirlines!!,"", data.airlinesFirst!!.listFlightTime,
                    data.airlinesFirst?.flightTime!!,data.airlinesFirst?.typeFlight!!, data.airlinesFirst?.cityCodeDeparture!!,
                    data.airlinesFirst?.cityCodeArrived!!, data.airlinesFirst?.priceTicket!!,
                    data.airlinesFirst?.departureTime!!, data.airlinesFirst?.arrivedTime!!)))

            ticket.addAll(listOf(Ticket(data.airlinesSecond?.nameAirlines!!,"", data.airlinesSecond!!.listFlightTime,
                    data.airlinesSecond?.flightTime!!,data.airlinesSecond?.typeFlight!!, data.airlinesSecond?.cityCodeDeparture!!,
                    data.airlinesSecond?.cityCodeArrived!!, data.airlinesSecond?.priceTicket!!,
                    data.airlinesSecond?.departureTime!!, data.airlinesSecond?.arrivedTime!!)))

            binding.rvTicket.apply {
                adapter = AdapterListTicket(requireActivity(), ticket, this@FillDataPassengerFragment)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        } else {
            ticket.addAll(listOf(Ticket(data.airlinesFirst?.nameAirlines!!,"", data.airlinesFirst!!.listFlightTime,
                    data.airlinesFirst?.flightTime!!,data.airlinesFirst?.typeFlight!!, data.airlinesFirst?.cityCodeDeparture!!,
                    data.airlinesFirst?.cityCodeArrived!!, data.airlinesFirst?.priceTicket!!,
                    data.airlinesFirst?.departureTime!!, data.airlinesFirst?.arrivedTime!!)))

            binding.rvTicket.apply {
                adapter = AdapterListTicket(requireActivity(), ticket, this@FillDataPassengerFragment)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onClick(view: View, data: Ticket) {
        Toast.makeText(requireActivity(), data.nameAirlines, Toast.LENGTH_SHORT).show()
    }

}