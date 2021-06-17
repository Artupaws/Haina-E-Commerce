package haina.ecommerce.view.flight.filldatapassenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterDataPassenger
import haina.ecommerce.adapter.flight.AdapterListTicket
import haina.ecommerce.databinding.FragmentFillDataPassengerBinding
import haina.ecommerce.model.flight.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.roomdatapassenger.DataPassenger
import haina.ecommerce.roomdatapassenger.PassengerDao
import haina.ecommerce.roomdatapassenger.RoomDataPassenger
import haina.ecommerce.util.Constants
import haina.ecommerce.view.paymentmethod.PaymentActivity


class FillDataPassengerFragment : Fragment(), View.OnClickListener,
    AdapterDataPassenger.ItemAdapterCallback, AdapterListTicket.ItemAdapterCallback, FillDataPassengerContract {

    private lateinit var _binding: FragmentFillDataPassengerBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var listDataPassenger: ArrayList<DataPassenger>
    private lateinit var data: Request
    private lateinit var database: RoomDataPassenger
    private lateinit var dao: PassengerDao
    private lateinit var presenter:FillDataPassengerPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFillDataPassengerBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireActivity())
        database = RoomDataPassenger.getDatabase(requireActivity())
        dao = database.getDataPassengerDao()
        presenter = FillDataPassengerPresenter(this, requireActivity())
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
        val airlineCode = arguments?.getString("airlineCode")
        val depart = arguments?.getParcelable<DepartItem>("depart")
        val returnParams = arguments?.getParcelable<DepartItem>("return")
        presenter.getCalculationTicketPrice(RequestPrice(airlineCode!!, depart, returnParams))
//        presenter.getCalculationTicketPrice(data.accessCode!!, data.depart as List<DepartItem>, data.returnParams)
        setDataOrderer()
        getListDataPassengerDao(database, dao)
        setlistTicket()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_data_passenger -> {
                val bundle = Bundle()
                bundle.putInt("totalPassenger", listDataPassenger.size)
                Navigation.findNavController(binding.btnAddDataPassenger)
                    .navigate(R.id.action_fillDataPassengerFragment_to_detailFillDataPassengerFragment, bundle)
            }
            R.id.btn_continue_payment -> {
                binding.relativeLoading.visibility = View.VISIBLE
                binding.btnContinuePayment.visibility = View.GONE
                checkDataPassenger()
//                if (listDataPassenger.size == 0) {
//                    Toast.makeText(requireActivity(), "Please fill in the passenger data according to the number", Toast.LENGTH_SHORT).show()
//                    binding.btnAddDataPassenger.visibility = View.VISIBLE
//                }
//                if (listDataPassenger.size == data.totalPassenger && listDataPassenger.size != 0) {
//                    binding.btnAddDataPassenger.visibility = View.GONE
//                    binding.btnContinuePayment.setOnClickListener {
//                        moveToPayment()
//                    }
//                }
//                setStateButtonContinue(data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getListDataPassengerDao(database, dao)
//        setStateButtonContinue(data)
    }

    private fun setDataOrderer() {
        binding.tvNameOrderer.text = sharedPref.getValueString(Constants.PREF_FULLNAME)
        binding.tvEmail.text = sharedPref.getValueString(Constants.PREF_EMAIL)
        binding.tvPhone.text = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)
    }

    private fun checkDataPassenger() {
        var codePhone = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)?.substring(0,1)
        val areaPhone = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)?.substring(1,4)
        val name = sharedPref.getValueString(Constants.PREF_FULLNAME)
        var title = sharedPref.getValueString(Constants.PREF_GENDER)
        val mainPhone = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)?.substring(4,sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)!!.length)
        if (codePhone == "0"){
            codePhone = "62"
        }
        title = if (title?.toLowerCase()?.contains("male") == true){
            "MR"
        } else {
            "MRS"
        }
        if (listDataPassenger.size == data.totalPassenger){
            val dataPassenger = listDataPassenger
            presenter.setDataPassenger(RequestSetPassenger(title, name!!, name, codePhone!!, areaPhone!!, mainPhone!!, null, dataPassenger))
        } else {
            binding.relativeLoading.visibility = View.GONE
            binding.btnContinuePayment.visibility = View.VISIBLE
            Toast.makeText(requireActivity(), "Please complete data passenger", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getListDataPassengerDao(database: RoomDataPassenger, dao: PassengerDao) {
        listDataPassenger = arrayListOf()
        listDataPassenger.addAll(dao.getAll())
        setupListDataPassenger(listDataPassenger)
        if (listDataPassenger.size == data.totalPassenger) {
            binding.btnAddDataPassenger.visibility = View.GONE
        } else {
            binding.btnAddDataPassenger.visibility = View.VISIBLE
        }
    }

    private fun setupListDataPassenger(listParams: ArrayList<DataPassenger>) {
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
                data.id_number?.let {
                    DataPassenger(data.id, data.first_name, data.birth_date, it, data.gender,
                        data.nationality, data.birth_country, data.last_name, data.title, data.parent,
                    data.passport_number, data.passport_issued_country, data.passport_issued_date, data.passport_expired_date, data.type) }?.let { deleteCart(it) }
                onResume()
            }

            R.id.tv_action_edit -> {
                Toast.makeText(requireActivity(), data.first_name, Toast.LENGTH_SHORT).show()
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

    override fun messageCalculationPrice(msg: String) {
        Log.d("getCalculation", msg)
    }

    override fun messageSetDataPassenger(msg: String) {
        Log.d("setDataPassenger", msg)
        binding.relativeLoading.visibility = View.INVISIBLE
        if (!msg.contains("Success!")){
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getCalculationPrice(data: DataRealTicketPrice?) {
        Toast.makeText(requireActivity(), data?.origin, Toast.LENGTH_SHORT).show()
    }

}