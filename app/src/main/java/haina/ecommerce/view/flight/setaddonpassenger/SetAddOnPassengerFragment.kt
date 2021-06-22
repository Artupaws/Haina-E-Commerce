package haina.ecommerce.view.flight.setaddonpassenger

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
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
import org.w3c.dom.Text

class SetAddOnPassengerFragment : Fragment(), SetAddOnContract, AdapterAddOn.ItemAdapterCallback {

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

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when (intent?.action) {
                "chooseSeat" -> {
                    popupShowChooseSeat?.show()
                }
                "addOn" -> {
                    val getListAddOn = intent.getParcelableArrayListExtra<BaggageInfosItem>("openDialog")
                    val getListMeals = intent.getParcelableArrayListExtra<MealInfosItem>("openDialogMeals")
                    dataBaggage = getListAddOn
                    dataMeals = getListMeals
                    popupSetAddOn?.show()
                }
                "priceAddOn" -> {
                    val price = intent.getIntExtra("price", 0)
                    totalPriceAddOn = price
                }
            }
            popupDialogSetAddOn(dataBaggage, dataMeals, totalPriceAddOn)
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


    @SuppressLint("UseCompatLoadingForDrawables")
    fun popupDialogSetAddOn(data: List<BaggageInfosItem?>?, dataMeals: List<MealInfosItem?>?, totalPriceParams: Int?) {
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
                Log.d("chooseBaggage", data?.get(p2)?.code.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        rvAddOn?.apply {
            adapter = AdapterAddOn(requireActivity(), dataMeals, this@SetAddOnPassengerFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        totalPrice?.text = "Total price Add-on : $totalPriceParams"


        ivClose?.setOnClickListener {
            popupSetAddOn?.dismiss()
        }

        btnSave?.setOnClickListener {

        }
    }

    override fun messageGetAddOn(msg: String) {
        Log.d("getDataAddOn", msg)
    }

    override fun getDataAddOn(data: DataAddOn?) {
        for (i in data?.addOns!!) {
            val adapterCombinePassengerAndFlight = AdapterCombinePassengerAndFlight(
                requireActivity(),
                dataPassengerParams,
                dataFlightParams,
                i?.baggageInfos,
                i?.mealInfos
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

                totalPrice?.text = "Total price -on : ${price.toString()}"

            }
        }
    }

}