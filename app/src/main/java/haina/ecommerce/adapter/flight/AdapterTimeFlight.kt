package haina.ecommerce.adapter.flight

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemTimeFlightBinding
import haina.ecommerce.model.flight.*

class AdapterTimeFlight(val context: Context, private val listAirlines: List<TimeFlight?>?) :
        RecyclerView.Adapter<AdapterTimeFlight.Holder>() {

    private var broadcaster:LocalBroadcastManager? =null
    private var indexItem:Int = 0
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemTimeFlightBinding.bind(view)
        fun bind(itemHaina: TimeFlight, position: Int) {
            with(binding) {
                itemView.setOnClickListener {
                    Log.d("positionItem", adapterPosition.toString())
                }
                if (adapterPosition+1 >= listAirlines!!.size){
                    binding.circleImageDestination.visibility = View.VISIBLE
                    binding.tvNameCityDestinationCode.visibility = View.VISIBLE
                } else {
                    binding.circleImageDestination.visibility = View.GONE
                    binding.tvNameCityDestinationCode.visibility = View.GONE
                }

                binding.tvNameCityCode.text = itemHaina.origin
                binding.tvTime.text = "${itemHaina.departureTime.substring(11, 19)} - ${itemHaina.arrivedTime.substring(11, 19)}"
                binding.tvNameCityDestinationCode.text = itemHaina.destination
                binding.circleImageDestination.setBackgroundResource(R.drawable.ic_complete_flight)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTimeFlight.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTimeFlightBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTimeFlight.Holder, position: Int) {
        val data: TimeFlight = listAirlines?.get(position)!!
        Log.d("isinya", position.toString())
        holder.bind(data, position)
    }

    override fun getItemCount(): Int = listAirlines!!.size
}