package haina.ecommerce.view.flight.filldatapassenger

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcelable
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterDataPassenger
import haina.ecommerce.adapter.flight.AdapterListTicket
import haina.ecommerce.databinding.FragmentFillDataPassengerBinding
import haina.ecommerce.model.flight.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.room.roomdatapassenger.DataPassenger
import haina.ecommerce.room.roomdatapassenger.PassengerDao
import haina.ecommerce.room.roomdatapassenger.RoomDataPassenger
import haina.ecommerce.util.Constants
import haina.ecommerce.view.paymentmethod.PaymentActivity


class FillDataPassengerFragment : Fragment(), View.OnClickListener,
    AdapterDataPassenger.ItemAdapterCallback, AdapterListTicket.ItemAdapterCallback, FillDataPassengerContract {

    private lateinit var _binding: FragmentFillDataPassengerBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var listDataPassenger: ArrayList<DataPassenger>
    private lateinit var dataRequest: Request
    private var departParams: DepartItem? = null
    private var returnParams: DepartItem? = null
    private var airlineCodeParams:String = ""
    private lateinit var database: RoomDataPassenger
    private lateinit var dao: PassengerDao
    private lateinit var presenter:FillDataPassengerPresenter
    private var popupInputCaptcha: Dialog? = null

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
            deleteAllPassenger()
            findNavController().navigateUp()

        }
        dataRequest = arguments?.getParcelable<Request>("data")!!
        val airlineCode = arguments?.getString("airlineCode")!!
        airlineCodeParams = airlineCode
        val depart = arguments?.getParcelable<DepartItem>("depart")
        departParams = depart
        val returnItem =arguments?.getParcelable<DepartItem>("return")
        returnParams =  returnItem
        presenter.getCalculationTicketPrice(RequestPrice(airlineCodeParams, departParams, returnParams, "1"))
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
        if (listDataPassenger.size == dataRequest.totalPassenger){
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
        if (listDataPassenger.size == dataRequest.totalPassenger) {
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
                    data.passport_number, data.passport_issued_country, data.passport_issued_date, data.passport_expired_date, data.type) }?.let { deletePassenger(it) }
                onResume()
            }

            R.id.tv_action_edit -> {
                Toast.makeText(requireActivity(), data.first_name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deletePassenger(datapassenger: DataPassenger){
        dao.delete(datapassenger)
    }

    private fun deleteAllPassenger() {
        dao.deleteAll()
    }

    private fun setlistTicket(){
        val ticket = mutableListOf<Ticket>()
        if (dataRequest.airlinesSecond != null){
            ticket.addAll(listOf(Ticket(dataRequest.airlinesFirst?.nameAirlines!!,"", dataRequest.airlinesFirst!!.listFlightTime,
                    dataRequest.airlinesFirst?.flightTime!!,dataRequest.airlinesFirst?.typeFlight!!, dataRequest.airlinesFirst?.cityCodeDeparture!!,
                    dataRequest.airlinesFirst?.cityCodeArrived!!, dataRequest.airlinesFirst?.priceTicket!!,
                    dataRequest.airlinesFirst?.departureTime!!, dataRequest.airlinesFirst?.arrivedTime!!)))

            ticket.addAll(listOf(Ticket(dataRequest.airlinesSecond?.nameAirlines!!,"", dataRequest.airlinesSecond!!.listFlightTime,
                    dataRequest.airlinesSecond?.flightTime!!,dataRequest.airlinesSecond?.typeFlight!!, dataRequest.airlinesSecond?.cityCodeDeparture!!,
                    dataRequest.airlinesSecond?.cityCodeArrived!!, dataRequest.airlinesSecond?.priceTicket!!,
                    dataRequest.airlinesSecond?.departureTime!!, dataRequest.airlinesSecond?.arrivedTime!!)))

            binding.rvTicket.apply {
                adapter = AdapterListTicket(requireActivity(), ticket, this@FillDataPassengerFragment)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        } else {
            ticket.addAll(listOf(Ticket(dataRequest.airlinesFirst?.nameAirlines!!,"", dataRequest.airlinesFirst!!.listFlightTime,
                    dataRequest.airlinesFirst?.flightTime!!,dataRequest.airlinesFirst?.typeFlight!!, dataRequest.airlinesFirst?.cityCodeDeparture!!,
                    dataRequest.airlinesFirst?.cityCodeArrived!!, dataRequest.airlinesFirst?.priceTicket!!,
                    dataRequest.airlinesFirst?.departureTime!!, dataRequest.airlinesFirst?.arrivedTime!!)))

            binding.rvTicket.apply {
                adapter = AdapterListTicket(requireActivity(), ticket, this@FillDataPassengerFragment)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogInputCaptcha(accessCode:String) {
        popupInputCaptcha = Dialog(requireActivity())
        popupInputCaptcha?.setContentView(R.layout.popup_image_captcha)
        popupInputCaptcha?.setCancelable(true)
        popupInputCaptcha?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupInputCaptcha?.window!!
        window.setGravity(Gravity.CENTER)
        val imageCaptcha =popupInputCaptcha?.findViewById<ImageView>(R.id.iv_image_captcha)
        val etCaptcha = popupInputCaptcha?.findViewById<EditText>(R.id.et_captcha)
        val buttonNext = popupInputCaptcha?.findViewById<Button>(R.id.btn_next)
        val decodedString: ByteArray = Base64.decode(accessCode, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        Glide.with(requireActivity()).load(decodedByte).into(imageCaptcha!!)
        buttonNext?.setOnClickListener {
            if (etCaptcha?.text.toString().isNotEmpty()){
                presenter.getCalculationTicketPrice(RequestPrice(airlineCodeParams, departParams, returnParams, etCaptcha.toString()))
            } else {
                etCaptcha?.error = "Please input captcha here"
            }
        }
    }

    override fun onClick(view: View, data: Ticket) {
        Toast.makeText(requireActivity(), data.nameAirlines, Toast.LENGTH_SHORT).show()
    }

    override fun messageCalculationPrice(msg: String) {
        Log.d("getCalculation", msg)
        if (!msg.contains("Success")){
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageSetDataPassenger(msg: String) {
        Log.d("setDataPassenger", msg)
        binding.relativeLoading.visibility = View.INVISIBLE
        binding.btnContinuePayment.visibility = View.VISIBLE
        if (!msg.contains("Success!")){
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getCalculationPrice(data: DataRealTicketPrice?) {
    }

    override fun accessCode(accessCode: String?) {
        if (accessCode != null) {
            if (accessCode.isNotEmpty()){
                popupDialogInputCaptcha(accessCode)
                popupInputCaptcha?.show()
            } else{
                popupInputCaptcha?.dismiss()
            }
        } else {
            popupInputCaptcha?.dismiss()
        }
    }

    override fun getIdSetPassenger(data: List<DataSetPassenger>) {
        Toast.makeText(requireContext(), data.size.toString(), Toast.LENGTH_SHORT).show()
        if (data.isNotEmpty()){
            val bundle = Bundle()
            val arrayListFlight = mutableListOf<Ticket>()
            if (dataRequest.airlinesSecond !=null){
                arrayListFlight.addAll(listOf(Ticket(dataRequest.airlinesFirst?.nameAirlines!!,"", dataRequest.airlinesFirst!!.listFlightTime,
                    dataRequest.airlinesFirst?.flightTime!!,dataRequest.airlinesFirst?.typeFlight!!, dataRequest.airlinesFirst?.cityCodeDeparture!!,
                    dataRequest.airlinesFirst?.cityCodeArrived!!, dataRequest.airlinesFirst?.priceTicket!!,
                    dataRequest.airlinesFirst?.departureTime!!, dataRequest.airlinesFirst?.arrivedTime!!)))

                arrayListFlight.addAll(listOf(Ticket(dataRequest.airlinesSecond?.nameAirlines!!,"", dataRequest.airlinesSecond!!.listFlightTime,
                    dataRequest.airlinesSecond?.flightTime!!,dataRequest.airlinesSecond?.typeFlight!!, dataRequest.airlinesSecond?.cityCodeDeparture!!,
                    dataRequest.airlinesSecond?.cityCodeArrived!!, dataRequest.airlinesSecond?.priceTicket!!,
                    dataRequest.airlinesSecond?.departureTime!!, dataRequest.airlinesSecond?.arrivedTime!!)))
            } else {
                arrayListFlight.addAll(listOf(Ticket(dataRequest.airlinesFirst?.nameAirlines!!,"", dataRequest.airlinesFirst!!.listFlightTime,
                    dataRequest.airlinesFirst?.flightTime!!,dataRequest.airlinesFirst?.typeFlight!!, dataRequest.airlinesFirst?.cityCodeDeparture!!,
                    dataRequest.airlinesFirst?.cityCodeArrived!!, dataRequest.airlinesFirst?.priceTicket!!,
                    dataRequest.airlinesFirst?.departureTime!!, dataRequest.airlinesFirst?.arrivedTime!!)))
            }
            bundle.putParcelableArrayList("dataSetPassenger", data as java.util.ArrayList)
            bundle.putParcelableArrayList("dataFlight", arrayListFlight as java.util.ArrayList<out Parcelable>)
            Navigation.findNavController(binding.btnContinuePayment).navigate(R.id.action_fillDataPassengerFragment_to_setAddOnPassengerFragment, bundle)
        }
    }

}