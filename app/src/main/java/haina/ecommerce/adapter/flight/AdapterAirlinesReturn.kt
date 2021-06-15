package haina.ecommerce.adapter.flight

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.get
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinay.stepview.models.Step
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListRoomHotel
import haina.ecommerce.databinding.ListItemAirlinesBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.flight.*
import haina.ecommerce.view.flight.chooseairline.ChooseAirlinesFragment

class AdapterAirlinesReturn(val context: Context, private val listAirlines: List<DepartItem?>?,
                            private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterAirlinesReturn.Holder>() {

    private var broadcaster:LocalBroadcastManager? =null
    private var helper:Helper = Helper
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemAirlinesBinding.bind(view)
        fun bind(itemHaina: DepartItem, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                tvAirlineName.text = itemHaina.airlineDetail?.airlineName
                val priceTicket = "${helper.convertToFormatMoneyIDRFilter(itemHaina.price.toString())}/person"
                tvPriceTicket.text = priceTicket
                setStepView(binding)
                linearClick.setOnClickListener {
                    itemAdapterCallback.onClick(binding.linearClick, itemHaina, itemHaina.flightTime) }
                setupListTimeFlight(binding, itemHaina.flightTime)
                if (itemHaina.flightTime?.size!! > 1){
                    tvTypeFlight.text = "Transit"
                } else {
                    tvTypeFlight.text = "Direct"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAirlinesReturn.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAirlinesBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAirlinesReturn.Holder, position: Int) {
        val depart: DepartItem = listAirlines?.get(position)!!
        holder.bind(depart, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listAirlines?.size!!

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setStepView(binding:ListItemAirlinesBinding){
        binding.stepView.completedStepTextColor = R.color.black
        binding.stepView.notCompletedStepTextColor = R.color.black
        binding.stepView.notCompletedStepIcon = context.getDrawable(R.drawable.ic_not_complete_flight)
        binding.stepView.completedStepIcon = context.getDrawable(R.drawable.ic_complete_flight)
        binding.stepView.completedLineColor = R.color.black
        binding.stepView.notCompletedLineColor = R.color.black
        binding.stepView.setCircleRadius(8F)
        binding.stepView.setLineLength(20F)
        binding.stepView.textSize = 12
        binding.stepView.setReverse(false)
    }

    private fun setupListTimeFlight(binding: ListItemAirlinesBinding, data:List<TimeFlight?>?){
        binding.rvTimeFlight.apply {
            adapter = AdapterTimeFlight(context,data)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    interface ItemAdapterCallback{
        fun onClick(view:View, data:DepartItem, timeFlight:List<TimeFlight?>?)
    }
}