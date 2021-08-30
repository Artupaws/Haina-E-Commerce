package haina.ecommerce.adapter.flight

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.LayoutInflater
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemFlightAddOnBinding
import haina.ecommerce.model.flight.*
import java.util.*


class AdapterListFlight(val context: Context, private val passengerId: Int,private val passengerType: Int, private val listAirlines: MutableList<Ticket>?,
                        private val dataAddons: List<AddOnsItem>?,val callback:CallbackInterface
) :
        RecyclerView.Adapter<AdapterListFlight.Holder>(){
    private var broadcaster : LocalBroadcastManager? = null

    private val STATUS_AVAILABLE = 1
    private val STATUS_RESERVED = 3
    private val STATUS_SELECTED = 4

    interface CallbackInterface {
        fun passDataAddons(passengerId:Int, dataAddonsAll:MutableList<TripAddonsData>, totalAddons:Int)
    }

    var dataAddonsAll:MutableList<TripAddonsData>? = mutableListOf()
    var totalAddonsPrice:Int = 0

    @Suppress("UNCHECKED_CAST")
    inner class Holder(view: View) : RecyclerView.ViewHolder(view),AdapterAddOn.CallbackInterface  {
        private val binding = ListItemFlightAddOnBinding.bind(view)
        private var popupSetAddOn: Dialog? = null
        private var popupShowChooseSeat: Dialog? = null
        lateinit var data:AddOnsItem
        var dataAddOn:List<BaggageInfosItem?>? = null
        var dataSeat:List<SeatInfosItem?>? = null
        var dataMeals:List<MealInfosItem?>? = null

        private var totalPrice: TextView? = null

        var totalSum:Int = 0
        var totalAddons:Int = 0
        var baggageFare:Int = 0
        var tvTotalPrice: TextView? = null
        var tvSeatSelected: TextView? = null
        var tvPriceSeat: TextView? = null
        var ticketPrice:Int = 0


        var selectedSeat:SeatInfosItem? = null

        var seat:HorizontalScrollView? = null
        var seatPrice:Int = 0

        var selectedBaggage:BaggageInfosItem? = null

        var selectedMeal:MutableList<MealInfosItem>? = null


        lateinit var dataaddons: TripAddonsData

        fun finalizeDataAddons(){
            dataAddonsAll?.remove(dataaddons)

            dataaddons.baggage=selectedBaggage?.code
            dataaddons.compartment=selectedSeat?.compartment
            dataaddons.seat=selectedSeat?.seatDesignator

            val listmeal:MutableList<String> = mutableListOf()
            selectedMeal?.forEach {
                listmeal.add(it.code!!)
            }

            dataaddons.meals=listmeal

            dataAddonsAll?.add(dataaddons)
            callback.passDataAddons(passengerId,dataAddonsAll!!,totalAddonsPrice)

        }

        fun bind(itemHaina: Ticket) {
            with(binding) {
                dataaddons=TripAddonsData(itemHaina.cityCodeDeparture,itemHaina.cityCodeArrived,null,null,null,null)
                dataAddonsAll?.add(dataaddons)
                if (dataAddons != null) {
                    for (i in dataAddons){
                        if(i.origin==itemHaina.cityCodeDeparture && i.destination==itemHaina.cityCodeArrived){
                            data=i
                        }
                    }

                    dataAddOn=data.baggageInfos
                    dataSeat=data.seatInfos
                    dataMeals=data.mealInfos

                    tvAirlineCode.text = itemHaina.nameAirlines
                    val originDestination = "${itemHaina.cityCodeDeparture} - ${itemHaina.cityCodeArrived}"
                    tvOriginDestination.text = originDestination
                    val originDestinationTime = "(${itemHaina.departureTime.substring(11, 19)} - ${itemHaina.arrivedTime.substring(11, 19)})"
                    tvTimeFlight.text = originDestinationTime

                    if(itemHaina.priceDetail?.size==1){
                        ticketPrice=(itemHaina.priceDetail?.find { it?.paxType == "Adult" }!!.baseFare?.div(
                            itemHaina.totalAdult
                        ))!!
                    }else{
                        when (passengerType) {
                            1 -> {
                                ticketPrice=(itemHaina.priceDetail?.find { it?.paxType == "Adult" }!!.baseFare?.div(
                                    itemHaina.totalAdult
                                ))!!
                            }
                            2 -> {

                                ticketPrice=(itemHaina.priceDetail?.find { it?.paxType == "Child" }!!.baseFare?.div(
                                    itemHaina.totalChild
                                ))!!
                            }
                            3 -> {
                                ticketPrice=(itemHaina.priceDetail?.find { it?.paxType == "Infant" }!!.baseFare?.div(
                                    itemHaina.totalBaby
                                ))!!
                            }
                        }
                    }

                    tvPriceFlight.text="Rp. " +ticketPrice



                    tvTotalPrice=tvFlightTotalPrice
                    tvSeatSelected=tvSelectedSeat
                    tvPriceSeat=tvSeatPrice

                    if(dataAddOn.isNullOrEmpty() && dataSeat.isNullOrEmpty() && dataMeals.isNullOrEmpty()){
                        layoutTotalPrice.visibility=View.GONE
                        view1.visibility=View.GONE
                    }

                    if(!dataAddOn.isNullOrEmpty()) {
                        val adapterSpinnerBaggage = AdapterSpinnerBaggage(context, dataAddOn)
                        spnBaggage?.adapter = adapterSpinnerBaggage
                        spnBaggage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                val priceBaggage = dataAddOn?.get(p2)?.fare!!
                                baggageFare=dataAddOn?.get(p2)?.fare!!
                                checkTotal()
                                selectedBaggage=dataAddOn?.get(p2)
                                finalizeDataAddons()
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }
                    }else{
                        layoutBaggage.visibility= View.GONE
                    }


                    if(!dataMeals.isNullOrEmpty()) {
                        popupDialogSetAddOn(
                            dataMeals,
                            itemHaina.cityCodeDeparture,
                            itemHaina.cityCodeArrived
                        )
                        btnAddOn.setOnClickListener {
                            popupSetAddOn?.show()
                        }

                    }else{
                        layoutSelected.visibility=View.GONE
                        layoutAddons.visibility=View.GONE
                    }


                    if(!dataSeat.isNullOrEmpty()){
                        popupDialogChooseSeat(
                            dataSeat,
                            itemHaina.cityCodeDeparture,
                            itemHaina.cityCodeArrived
                        )
                        btnChooseSeat.setOnClickListener {
                            popupShowChooseSeat?.show()
                        }
                    }else{
                        layoutChooseSeat.visibility = View.GONE
                    }

                    checkTotal()
                    finalizeDataAddons()
                }



            }
        }

        override fun passTotal(sum: Int, meal: MutableList<MealInfosItem>) {

            binding.layoutSelectedAddons.removeAllViewsInLayout()
            totalAddons=0

            selectedMeal=meal

            meal.forEach{
                val li = LayoutInflater.from(context)
                val theview = li.inflate(R.layout.list_item_selected_addons, null)
                val name = theview.findViewById(R.id.tv_name) as TextView
                val price = theview.findViewById(R.id.tv_price) as TextView

                name.text= it.desc
                price.text= it.fare.toString()

                totalAddons+= it.fare!!
                binding.layoutSelectedAddons.addView(theview)
                checkTotal()
            }
            totalPrice?.text=totalAddons.toString()

            finalizeDataAddons()

        }

        fun popupDialogSetAddOn( dataMeals: List<MealInfosItem?>?, departure: String, arrival: String) {
            popupSetAddOn = Dialog(context)
            popupSetAddOn?.setContentView(R.layout.layout_popup_dialog_addon_flight)
            popupSetAddOn?.setCancelable(true)
            popupSetAddOn?.window?.setBackgroundDrawableResource(R.color.white)
            val window: Window = popupSetAddOn?.window!!
            window.setGravity(Gravity.CENTER)
            val searchAddOn = popupSetAddOn?.findViewById<SearchView>(R.id.sv_addOn)
            val rvAddOn = popupSetAddOn?.findViewById<RecyclerView>(R.id.rv_addon)
            val btnSave = popupSetAddOn?.findViewById<Button>(R.id.btn_save)
            val ivClose = popupSetAddOn?.findViewById<ImageView>(R.id.iv_close)
            totalPrice = popupSetAddOn?.findViewById<TextView>(R.id.tv_total_price)
            totalPrice?.text= "Total : $totalSum"

            rvAddOn?.apply {
                adapter = AdapterAddOn(context, dataMeals,this@Holder)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            ivClose?.setOnClickListener {
                popupSetAddOn?.hide()
            }

            btnSave?.setOnClickListener{
                popupSetAddOn?.hide()
            }
        }
        fun clearSelectedSeat(){
            val layout = seat!!.getChildAt(seat!!.childCount - 1) as LinearLayout
            for (i in 0 until layout.childCount){
                val lay=layout.getChildAt(i) as LinearLayout
                for (o in 0 until lay.childCount){
                    val a = lay.getChildAt(o) as TextView
                    if(a.tag==STATUS_SELECTED){
                        a.setBackgroundResource(R.drawable.ic_seats_book)
                        a.tag=STATUS_AVAILABLE
                    }
                }
            }
        }
        fun popupDialogChooseSeat(dataSeat: List<SeatInfosItem?>?, departure: String, arrival: String) {
            popupShowChooseSeat = Dialog(context)
            popupShowChooseSeat?.setContentView(R.layout.layout_pop_up_choose_seat)
            popupShowChooseSeat?.setCancelable(true)
            popupShowChooseSeat?.window?.setBackgroundDrawableResource(R.color.white)
            val title = popupShowChooseSeat?.findViewById<TextView>(R.id.tv_title)
            val price = popupShowChooseSeat?.findViewById<TextView>(R.id.tv_total_price)
            val btnSave = popupShowChooseSeat?.findViewById<Button>(R.id.btn_save)
            val scrollSeat = popupShowChooseSeat?.findViewById<NestedScrollView>(R.id.scroll_seat)
            seat = popupShowChooseSeat?.findViewById<HorizontalScrollView>(R.id.layoutSeat)


            val seatGaping = 8
            val layoutSeat = LinearLayout(context)

            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layoutSeat.orientation = LinearLayout.VERTICAL
            layoutSeat.layoutParams = params
            layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping)
            seat!!.addView(layoutSeat)



            var count = 0
            val seatSize = 40



            var seatViewList= ArrayList<TextView>()

            var number= mutableListOf<SeatInfosItem>()
            var letter= mutableListOf<SeatInfosItem>()

            if (dataSeat != null) {
                for (i in dataSeat){
                    i!!.X = i!!.X?.plus(2)
                    i!!.Y = i!!.Y?.plus(2)
                }
                var maxY = dataSeat.maxByOrNull { it!!.Y!! }?.Y!!
                var maxX = dataSeat.maxByOrNull { it!!.X!! }?.X!!

                var data= arrayListOf<ArrayList<SeatInfosItem>>()

                for (i in 0 until maxY ){
                    var cols=ArrayList<SeatInfosItem>()
                    for (o in 0 until maxX){
                        var seat=dataSeat.filter { it!!.X==o && it!!.Y==i }
                        if(seat.isNotEmpty()){
                            cols.add(seat[0]!!)
                        }else{
                            cols.add(SeatInfosItem())
                        }
                    }
                    data.add(cols)
                }

                var rowCount=0

                var layout = LinearLayout(context)
                layout.orientation = LinearLayout.HORIZONTAL
                layoutSeat.addView(layout)
                for (i in data) {
                    var colCount=0

                    var layout = LinearLayout(context)
                    layout.orientation = LinearLayout.HORIZONTAL

                    for (o in i){
                        when {
                            o.seatType.equals(null) -> {
                                val view = TextView(context)
                                val layoutParams: LinearLayout.LayoutParams =
                                    LinearLayout.LayoutParams(seatSize*2, seatSize*2)
                                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                                view.layoutParams = layoutParams
                                view.setBackgroundColor(Color.TRANSPARENT)
                                view.text = ""
                                layout!!.addView(view)
                            }
                            o.seatType.equals("LR") -> {
                                val view = TextView(context)
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
//                            view.setOnClickListener(this)
                                colCount++
                            }
                            o.seatType.equals("NS") -> {
                                if (o.isOpen==false ){

                                    count++
                                    val view = TextView(context)
                                    val layoutParams: LinearLayout.LayoutParams =
                                        LinearLayout.LayoutParams(seatSize*o.width!!, seatSize*o.height!!)
                                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                                    view.layoutParams = layoutParams
                                    view.setPadding(0, 0, 0, 2 * seatGaping)
                                    view.id = count
                                    view.gravity = Gravity.CENTER
                                    view.setBackgroundResource(R.drawable.ic_seats_reserved)
                                    view.text = o.seatDesignator!!
                                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                                    view.setTextColor(Color.WHITE)
                                    view.tag = STATUS_RESERVED
                                    layout!!.addView(view)
                                    seatViewList.add(view)
                                }else{
                                    count++
                                    val view = TextView(context)
                                    val layoutParams: LinearLayout.LayoutParams =
                                        LinearLayout.LayoutParams(seatSize*o.width!!, seatSize*o.height!!)
                                    layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                                    view.layoutParams = layoutParams
                                    view.setPadding(0, 0, 0, 2 * seatGaping)
                                    view.id = count
                                    view.gravity = Gravity.CENTER
                                    view.setBackgroundResource(R.drawable.ic_seats_book)
                                    view.text = o.seatDesignator!!
                                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                                    view.setTextColor(Color.BLACK)
                                    view.tag = STATUS_AVAILABLE
                                    layout!!.addView(view)
                                    seatViewList.add(view)
                                    view.setOnClickListener{
                                        if(it.tag==STATUS_AVAILABLE){
                                            clearSelectedSeat()
                                            it.setBackgroundResource(R.drawable.ic_seats_selected)
                                            it.tag=STATUS_SELECTED
                                            price!!.text=o.seatPrice.toString()
                                            selectedSeat=o
                                            tvSeatSelected!!.text=o.seatDesignator!!
                                            tvPriceSeat!!.text=o.seatPrice!!.toString()
                                            seatPrice=o.seatPrice!!
                                            checkTotal()

                                            finalizeDataAddons()

                                        }else{

                                            it.setBackgroundResource(R.drawable.ic_seats_book)
                                            it.tag=STATUS_AVAILABLE
                                            price!!.text="0"
                                            selectedSeat=null
                                            tvSeatSelected!!.text=""
                                            tvPriceSeat!!.text=""
                                            seatPrice=0
                                            checkTotal()

                                            finalizeDataAddons()

                                        }
                                    }
                                }
                                colCount++

                            }
                            else -> {
                                val view = TextView(context)
                                val layoutParams: LinearLayout.LayoutParams =
                                    LinearLayout.LayoutParams(seatSize*2, seatSize*2)
                                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                                view.layoutParams = layoutParams
                                view.setBackgroundColor(Color.TRANSPARENT)
                                view.text = ""
                                layout!!.addView(view)
                            }
                        }
                    }

                    if(colCount>0){
                        layoutSeat.addView(layout)
                        rowCount=0
                    }else if(colCount==0 && rowCount<=1){
                        layoutSeat.addView(layout)
                        rowCount++
                    }else if(colCount==0 && rowCount>1){
                        rowCount++
                    }
                }
                val window: Window = popupShowChooseSeat?.window!!
                window.setGravity(Gravity.CENTER)
            }


        }

        fun checkTotal(){
            totalAddonsPrice-=totalSum
            totalSum=totalAddons+baggageFare+seatPrice+ticketPrice
            tvTotalPrice?.text= "Total : $totalSum"
            totalAddonsPrice+=totalSum




        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListFlight.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFlightAddOnBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListFlight.Holder, position: Int) {
        val photo: Ticket = listAirlines?.get(position)!!
        broadcaster = LocalBroadcastManager.getInstance(context)
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listAirlines?.size!!


}