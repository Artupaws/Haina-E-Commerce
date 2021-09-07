package haina.ecommerce.adapter.flight

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemAirlinesBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.flight.*

class AdapterAirlines(val context: Context, private val listAirlines: DataAirline,
                      private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterAirlines.Holder>() {

    private var broadcaster:LocalBroadcastManager? =null
    private var helper:Helper = Helper
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemAirlinesBinding.bind(view)
        fun bind(itemHaina: DepartItem, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                tvAirlineName.text = itemHaina.airlineDetail?.airlineName
                val priceTicket = "${helper.convertToFormatMoneyIDRFilter(itemHaina.price.toString())}"
                tvPriceTicket.text = priceTicket
                setStepView(binding)
                linearClick.setOnClickListener {
                    itemAdapterCallback.onClick(binding.linearClick, itemHaina, itemHaina.flightTime, listAirlines.depart, listAirlines.returnParams) }
                setupListTimeFlight(binding, itemHaina.flightTime)
                if (itemHaina.flightTime?.size!! > 1){
                    tvTypeFlight.text = "Transit"
                } else {
                    tvTypeFlight.text = "Direct"
                }
                Glide.with(context).load(itemHaina.airlineDetail?.image).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding?.ivAirlineIcon!!)

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
        val depart: DepartItem = listAirlines.depart[position]
        holder.bind(depart, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listAirlines.depart.size

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
        fun onClick(view: View, data: DepartItem, timeFlight: List<TimeFlight?>?, depart: List<DepartItem>, returnParams: List<DepartItem?>?)
    }
}