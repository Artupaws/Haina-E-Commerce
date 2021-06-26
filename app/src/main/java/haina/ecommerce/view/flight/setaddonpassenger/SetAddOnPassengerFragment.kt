package haina.ecommerce.view.flight.setaddonpassenger

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAddOn
import haina.ecommerce.adapter.flight.AdapterCombinePassengerAndFlight
import haina.ecommerce.adapter.flight.AdapterSpinnerBaggage
import haina.ecommerce.databinding.FragmentSetAddOnPassengerBinding
import haina.ecommerce.model.flight.*
import kotlin.math.log


class SetAddOnPassengerFragment : Fragment(), SetAddOnContract, AdapterAddOn.ItemAdapterCallback, View.OnClickListener {

    private lateinit var _binding: FragmentSetAddOnPassengerBinding
    private val binding get() = _binding
    private var dataPassengerParams = java.util.ArrayList<DataSetPassenger>()
    private var dataFlightParams = java.util.ArrayList<Ticket>()
    private lateinit var presenter: SetAddOnPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var popupShowChooseSeat: Dialog? = null
    private var popupSetAddOn: Dialog? = null
    private var totalPriceAddOn: Int = 0
    private var totalPrice: TextView? = null
    private var dataBaggage: List<BaggageInfosItem?>? = null
    private var dataMeals: List<MealInfosItem?>? = null

    private val STATUS_AVAILABLE = 1
    private val STATUS_BOOKED = 2
    private val STATUS_RESERVED = 3

    private var selectedIds = ""

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

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when (intent?.action) {
                "chooseSeat" -> {
                    val dataSeats = intent.getParcelableArrayListExtra<SeatInfosItem>("dataSeats")
                    val departure = intent.getStringExtra("departure")
                    val arrival = intent.getStringExtra("arrival")
                    popupDialogChooseSeat(dataSeats,departure,arrival)
                    popupShowChooseSeat?.show()
                }
                "addOn" -> {
                    val getListAddOn = intent.getParcelableArrayListExtra<BaggageInfosItem>("openDialog")
                    val getListMeals = intent.getParcelableArrayListExtra<MealInfosItem>("openDialogMeals")
                    val departure = intent.getStringExtra("departure")
                    val arrival = intent.getStringExtra("arrival")

                    dataBaggage = getListAddOn
                    dataMeals = getListMeals

                    popupDialogSetAddOn(dataBaggage, dataMeals,departure,arrival)

                    popupSetAddOn?.show()
                }
//                "priceAddOn" -> {
//                    val price = intent.getIntExtra("price", 0)
//                    totalPriceAddOn = price
//                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("chooseSeat"))
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("addOn"))
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("priceAddOn"))
    }

    override fun onStop() {
        super.onStop()
        broadcaster?.unregisterReceiver(mMessageReceiver)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogChooseSeat(dataSeat: List<SeatInfosItem>, departure: String, arrival: String) {
        popupShowChooseSeat = Dialog(requireActivity())
        popupShowChooseSeat?.setContentView(R.layout.layout_pop_up_choose_seat)
        popupShowChooseSeat?.setCancelable(true)
        popupShowChooseSeat?.window?.setBackgroundDrawableResource(R.color.white)
        val title = popupShowChooseSeat?.findViewById<TextView>(R.id.tv_title)
        val btnSave = popupShowChooseSeat?.findViewById<Button>(R.id.btn_save)
        val scrollSeat = popupShowChooseSeat?.findViewById<NestedScrollView>(R.id.scroll_seat)
        val seat = popupShowChooseSeat?.findViewById<HorizontalScrollView>(R.id.layoutSeat)


        val seatGaping = 8
        val layoutSeat = LinearLayout(requireActivity())

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutSeat.orientation = LinearLayout.VERTICAL
        layoutSeat.layoutParams = params
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping)
        seat!!.addView(layoutSeat)

        var layout: LinearLayout? = null

        var seats = ("_UUUUUUAAAAARRRR_/"
                + "_________________/"
                + "UU__AAAARRRRR__RR/"
                + "UU__UUUAAAAAA__AA/"
                + "AA__AAAAAAAAA__AA/"
                + "AA__AARUUUURR__AA/"
                + "UU__UUUA_RRRR__AA/"
                + "AA__AAAA_RRAA__UU/"
                + "AA__AARR_UUUU__RR/"
                + "AA__UUAA_UURR__RR/"
                + "_________________/"
                + "UU_AAAAAAAUUUU_RR/"
                + "RR_AAAAAAAAAAA_AA/"
                + "AA_UUAAAAAUUUU_AA/"
                + "AA_AAAAAAUUUUU_AA/"
                + "UU_AAAAAAAUUUU_RR/"
                + "RR_AAAAAAAAAAA_AA/"
                + "AA_UUAAAAAUUUU_AA/"
                + "AA_AAAAAAUUUUU_AA/"
                + "UU_AAAAAAAUUUU_RR/"
                + "RR_AAAAAAAAAAA_AA/"
                + "AA_UUAAAAAUUUU_AA/"
                + "AA_AAAAAAUUUUU_AA/"
                + "UU_AAAAAAAUUUU_RR/"
                + "RR_AAAAAAAAAAA_AA/"
                + "AA_UUAAAAAUUUU_AA/"
                + "AA_AAAAAAUUUUU_AA/"
                + "_________________/")

        var count = 0
        val seatSize = 40

        seats = "/" + seats;


        var seatViewList= ArrayList<TextView>()

//        for (index in seats.indices) {
//            when {
//                seats[index] == '/' -> {
//                    layout = LinearLayout(requireActivity())
//                    layout.orientation = LinearLayout.HORIZONTAL
//                    layoutSeat.addView(layout)
//                }
//                seats[index] == 'U' -> {
//                    count++
//                    val view = TextView(requireActivity())
//                    val layoutParams: LinearLayout.LayoutParams =
//                        LinearLayout.LayoutParams(seatSize, seatSize)
//                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
//                    view.layoutParams = layoutParams
//                    view.setPadding(0, 0, 0, 2 * seatGaping)
//                    view.id = count
//                    view.gravity = Gravity.CENTER
//                    view.setBackgroundResource(R.drawable.ic_seats_booked)
//                    view.setTextColor(Color.WHITE)
//                    view.tag = STATUS_BOOKED
//                    view.text = count.toString() + ""
//                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
//                    layout!!.addView(view)
//                    seatViewList.add(view)
//                    view.setOnClickListener(this)
//                }
//                seats[index] == 'A' -> {
//                    count++
//                    val view = TextView(requireActivity())
//                    val layoutParams: LinearLayout.LayoutParams =
//                        LinearLayout.LayoutParams(seatSize, seatSize)
//                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
//                    view.layoutParams = layoutParams
//                    view.setPadding(0, 0, 0, 2 * seatGaping)
//                    view.id = count
//                    view.gravity = Gravity.CENTER
//                    view.setBackgroundResource(R.drawable.ic_seats_book)
//                    view.text = count.toString() + ""
//                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
//                    view.setTextColor(Color.BLACK)
//                    view.tag = STATUS_AVAILABLE
//                    layout!!.addView(view)
//                    seatViewList.add(view)
//                    view.setOnClickListener(this)
//                }
//                seats[index] == 'R' -> {
//                    count++
//                    val view = TextView(requireActivity())
//                    val layoutParams: LinearLayout.LayoutParams =
//                        LinearLayout.LayoutParams(seatSize, seatSize)
//                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
//                    view.layoutParams = layoutParams
//                    view.setPadding(0, 0, 0, 2 * seatGaping)
//                    view.id = count
//                    view.gravity = Gravity.CENTER
//                    view.setBackgroundResource(R.drawable.ic_seats_reserved)
//                    view.text = count.toString() + ""
//                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
//                    view.setTextColor(Color.WHITE)
//                    view.tag = STATUS_RESERVED
//                    layout!!.addView(view)
//                    seatViewList.add(view)
//                    view.setOnClickListener(this)
//                }
//                seats[index] == '_' -> {
//                    val view = TextView(requireActivity())
//                    val layoutParams: LinearLayout.LayoutParams =
//                        LinearLayout.LayoutParams(seatSize, seatSize)
//                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
//                    view.layoutParams = layoutParams
//                    view.setBackgroundColor(Color.TRANSPARENT)
//                    view.text = ""
//                    layout!!.addView(view)
//                }
//            }
//        }
        var number= mutableListOf<SeatInfosItem>()
        var letter= mutableListOf<SeatInfosItem>()

        for (i in dataSeat){
            Log.d("x",i.X.toString())
            i.X = i.X?.plus(2)
            i.Y = i.Y?.plus(2)
        }

        var maxY = dataSeat.maxByOrNull { it.Y!! }?.Y!!
        var maxX = dataSeat.maxByOrNull { it.X!! }?.X!!

        var data= arrayListOf<ArrayList<SeatInfosItem>>()
        Log.d("max",maxX.toString()+"x"+maxY.toString())

        for (i in 0 until maxY ){
            var cols=ArrayList<SeatInfosItem>()
            for (o in 0 until maxX){
                var seat=dataSeat.filter { it.X==o && it.Y==i }
                if(seat.isNotEmpty()){
                    cols.add(seat[0])
                }else{
                    cols.add(SeatInfosItem())
                }
            }
            data.add(cols)
        }

        layout = LinearLayout(requireActivity())
        layout.orientation = LinearLayout.HORIZONTAL
        layoutSeat.addView(layout)

        for (i in data) {
            for (o in i){
                when {
                    o.seatType.equals(null) -> {
                        val view = TextView(requireActivity())
                        val layoutParams: LinearLayout.LayoutParams =
                            LinearLayout.LayoutParams(seatSize*2, seatSize*2)
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                        view.layoutParams = layoutParams
                        view.setBackgroundColor(Color.TRANSPARENT)
                        view.text = ""
                        layout!!.addView(view)
                    }
                    o.seatType.equals("LR") -> {
                        val view = TextView(requireActivity())
                        val layoutParams: LinearLayout.LayoutParams =
                            LinearLayout.LayoutParams(seatSize*o.width!!, seatSize*o.height!!)
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                        view.layoutParams = layoutParams
                        view.setPadding(0, 0, 0, 2 * seatGaping)
                        view.gravity = Gravity.CENTER
                        view.setBackgroundResource(R.drawable.ic_seats_reserved)
                        view.text = o.seatText + ""
                        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                        view.setTextColor(Color.BLACK)
                        view.tag = STATUS_AVAILABLE
                        layout!!.addView(view)
                        seatViewList.add(view)
                        view.setOnClickListener(this)
                    }
                    o.seatType.equals("NS") -> {
                        count++
                        val view = TextView(requireActivity())
                        val layoutParams: LinearLayout.LayoutParams =
                            LinearLayout.LayoutParams(seatSize*o.width!!, seatSize*o.height!!)
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                        view.layoutParams = layoutParams
                        view.setPadding(0, 0, 0, 2 * seatGaping)
                        view.id = count
                        view.gravity = Gravity.CENTER
                        view.setBackgroundResource(R.drawable.ic_seats_book)
                        view.text = count.toString() + ""
                        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                        view.setTextColor(Color.BLACK)
                        view.tag = STATUS_AVAILABLE
                        layout!!.addView(view)
                        seatViewList.add(view)
                        view.setOnClickListener(this)
                    }
                    else -> {
                        val view = TextView(requireActivity())
                        val layoutParams: LinearLayout.LayoutParams =
                            LinearLayout.LayoutParams(seatSize*o.width!!, seatSize*o.height!!)
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                        view.layoutParams = layoutParams
                        view.setBackgroundColor(Color.TRANSPARENT)
                        view.text = ""
                        layout!!.addView(view)
                    }
                }
            }
            layout = LinearLayout(requireActivity())
            layout.orientation = LinearLayout.HORIZONTAL
            layoutSeat.addView(layout)
        }
        val window: Window = popupShowChooseSeat?.window!!
        window.setGravity(Gravity.CENTER)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun popupDialogSetAddOn(data: List<BaggageInfosItem?>?, dataMeals: List<MealInfosItem?>?, departure: String, arrival: String) {
        popupSetAddOn = Dialog(requireActivity())
        popupSetAddOn?.setContentView(R.layout.layout_popup_dialog_addon_flight)
        popupSetAddOn?.setCancelable(true)
        popupSetAddOn?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupSetAddOn?.window!!
        window.setGravity(Gravity.CENTER)
        val searchAddOn = popupSetAddOn?.findViewById<SearchView>(R.id.sv_addOn)
        val spinnerBaggage = popupSetAddOn?.findViewById<Spinner>(R.id.spn_baggage)
        val rvAddOn = popupSetAddOn?.findViewById<RecyclerView>(R.id.rv_addon)
        val btnSave = popupSetAddOn?.findViewById<Button>(R.id.btn_save)
        val ivClose = popupSetAddOn?.findViewById<ImageView>(R.id.iv_close)
        totalPrice = popupSetAddOn?.findViewById<TextView>(R.id.tv_total_price)
        val adapterSpinnerBaggage = AdapterSpinnerBaggage(requireActivity(), data)
        spinnerBaggage?.adapter = adapterSpinnerBaggage
        spinnerBaggage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val priceBaggage = data?.get(p2)?.fare!!
                val priceBaggageValue = totalPriceAddOn+priceBaggage
                totalPriceAddOn = priceBaggageValue
                totalPrice?.text = priceBaggageValue.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        rvAddOn?.apply {
            adapter = AdapterAddOn(requireActivity(), dataMeals, this@SetAddOnPassengerFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        ivClose?.setOnClickListener {
            popupSetAddOn?.cancel()
        }

        btnSave?.setOnClickListener {

        }
    }

    override fun messageGetAddOn(msg: String) {
        Log.d("getDataAddOn", msg)
    }

    override fun getDataAddOn(data: List<AddOnsItem>?) {
        for (i in data!!) {
            val adapterCombinePassengerAndFlight = AdapterCombinePassengerAndFlight(
                requireActivity(),
                dataPassengerParams,
                dataFlightParams,
                i?.baggageInfos,
                i?.mealInfos,
                i?.seatInfos


                )
            binding.rvSetAddOn.apply {
                adapter = adapterCombinePassengerAndFlight
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        }
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
            Toast.makeText(requireActivity(), "Seat " + p0!!.id.toString() + " is Booked", Toast.LENGTH_SHORT)
                .show()
        } else if (p0!!.tag as Int == STATUS_RESERVED) {
            Toast.makeText(
                requireActivity(),
                "Seat " + p0!!.id.toString() + " is Reserved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}