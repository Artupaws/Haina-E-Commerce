package haina.ecommerce.adapter.flight

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.vinay.stepview.models.Step
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListRoomHotel
import haina.ecommerce.databinding.ListItemAirlinesBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.flight.Airlines
import haina.ecommerce.model.flight.AirlinesFirst
import haina.ecommerce.model.flight.DestinationCity
import haina.ecommerce.model.flight.TimeFlight

class AdapterAirlines(val context: Context, private val listAirlines: ArrayList<Airlines>,
                      private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterAirlines.Holder>() {

    private var broadcaster:LocalBroadcastManager? =null
    private var helper:Helper = Helper
    private lateinit var listStep :List<Step>
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemAirlinesBinding.bind(view)
        fun bind(itemHaina: Airlines, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                listStep = listOf(
                        Step(itemHaina.listFlightTime?.get(0)?.departureTime!!, Step.State.NOT_COMPLETED),
                        Step(itemHaina.listFlightTime?.get(0)?.arrivedTime!!, Step.State.COMPLETED),
                )
                stepView.steps = listStep
                tvAirlineName.text = itemHaina.nameAirlines
                val priceTicket = "${helper.convertToFormatMoneyIDRFilter(itemHaina.priceTicket)}/person"
                tvPriceTicket.text = priceTicket
                val typeAndTotalFlight = "${itemHaina.typeFlight} | ${itemHaina.flightTime}"
                tvTypeAndTotalFlightTime.text = typeAndTotalFlight
                setStepView(binding)
                itemView.setOnClickListener { itemAdapterCallback.onClick(itemView, itemHaina) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAirlines.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAirlinesBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAirlines.Holder, position: Int) {
        val photo: Airlines = listAirlines?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listAirlines?.size!!

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setStepView(binding:ListItemAirlinesBinding){
        binding.stepView.completedStepTextColor = R.color.black
        binding.stepView.notCompletedStepTextColor = R.color.black
        binding.stepView.notCompletedStepIcon = context.getDrawable(R.drawable.ic_not_complete_flight)
        binding.stepView.completedStepIcon = context.getDrawable(R.drawable.ic_complete_flight)
        binding.stepView.setCompletedLineColor(R.color.black)
        binding.stepView.setNotCompletedLineColor(R.color.black)
        binding.stepView.setCircleRadius(8F)
        binding.stepView.setLineLength(20F)
        binding.stepView.textSize = 12
        binding.stepView.setReverse(false)
    }

    interface ItemAdapterCallback{
        fun onClick(view:View, data:Airlines)
    }

}