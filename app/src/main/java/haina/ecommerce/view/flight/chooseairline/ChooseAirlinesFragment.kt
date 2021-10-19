package haina.ecommerce.view.flight.chooseairline

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAirlines
import haina.ecommerce.databinding.FragmentChooseAirlinesBinding
import haina.ecommerce.model.flight.*

class ChooseAirlinesFragment : Fragment(), AdapterAirlines.ItemAdapterCallback, ChooseAirlineFirstContract {

    private lateinit var _binding: FragmentChooseAirlinesBinding
    private val binding get() = _binding
    private lateinit var data: Request
    private var popupInputCaptcha: Dialog? = null
    private var tripType:String = ""
    private lateinit var presenter: ChooseAirlineFirstPresenter
    private var airlineCode:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChooseAirlinesBinding.inflate(inflater, container, false)
        presenter = ChooseAirlineFirstPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = arguments?.getParcelable("data")!!
        Log.d("typeTrip", data.finishDate.toString())
        tripType = if (data.finishDate.isNullOrEmpty()){
            "OneWay"
        } else {
            "RoundTrip"
        }
        presenter.getAirlinesData(tripType, data.fromDestination, data.toDestination, data.startDate, data.finishDate, data.totalAdult, data.totalChild,
            data.totalBaby, "1")
        binding.toolbarChooseAirlines.title = "${data.fromDestination} - ${data.toDestination}"
        binding.toolbarChooseAirlines.subtitle = "${data.startDate} | ${data.totalPassenger}"
        binding.toolbarChooseAirlines.setNavigationOnClickListener {
            findNavController().navigateUp()
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
                presenter.getAirlinesData(tripType, data.fromDestination, data.toDestination, data.startDate, data.finishDate, data.totalAdult, data.totalChild,
                    data.totalBaby, etCaptcha?.text.toString())
            } else {
                etCaptcha?.error = getString(R.string.captcha)
            }
        }
    }

    override fun messageChooseAirline(msg: String) {
        Log.d("getAirlines", msg)
        if (msg.contains("Success")){
            popupInputCaptcha?.dismiss()
        }
    }

    override fun accessCode(accessCode: String?) {
        Log.d("isinya", accessCode!!)
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

    override fun getDataAirline(data: DataAirline?) {
        Log.d("isinya", data?.accessCode.toString())
        binding.rvAirlines.apply {
            adapter = AdapterAirlines(requireContext(), data!!, this@ChooseAirlinesFragment)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(
        view: View, dataDepart: DepartItem, timeFlight: List<TimeFlight?>?, depart: List<DepartItem>, returnParams: List<DepartItem?>?) {
        when(view.id){
            R.id.linear_click -> {
                val dataFlight = Request(this.data.startDate, this.data.finishDate, this.data.fromDestination, this.data.toDestination,
                    this.data.totalPassenger, this.data.totalAdult, this.data.totalChild, this.data.totalBaby, airlinesFirst = AirlinesFirst(
                        dataDepart.airlineCode!!, dataDepart.airlineDetail?.image!!, null, dataDepart.departTime!!, "Direct Flight",
                        dataDepart.origin!!, dataDepart.destination!!, dataDepart.price.toString(), dataDepart.departTime.toString(), dataDepart.arrivalTime!!), null, null,
                     depart, returnParams)
                airlineCode = dataDepart.airlineCode
                val bundle = Bundle()
                bundle.putParcelable("data", dataFlight)
                bundle.putParcelable("depart", dataDepart)
                bundle.putString("airlineCode", airlineCode)
                if (this.data.finishDate.isNullOrEmpty()){
                    Navigation.findNavController(view).navigate(R.id.action_chooseAirlinesFragment_to_fillDataPassengerFragment, bundle)
                } else if (!this.data.finishDate?.contains("select date")!!){
                    Navigation.findNavController(view).navigate(R.id.action_chooseAirlinesFragment_to_chooseAirlinesSecondFlightFragment, bundle)
                }
            }
        }
    }

    fun getAirlineCode():String{
        return airlineCode
    }

}