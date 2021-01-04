package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemLocationFilterJobBinding
import haina.ecommerce.model.DataItemHaina

class AdapterLocationFilterJob(private val context: Context, private val jobList: List<DataItemHaina?>?): RecyclerView.Adapter<AdapterLocationFilterJob.Holder>() {

    private var index:Int = -1
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemLocationFilterJobBinding.bind(itemView)
        fun bind(itemHaina: DataItemHaina){
            with(binding){
                tvLocationJob.text = itemHaina.name
                linearLocation.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    val setJobLocation = Intent("jobLocationFilter")
                            .putExtra("nameLocationFilter", tvLocationJob.text.toString())
                        .putExtra("idLocationFilter", itemHaina.id.toString())
                    broadcaster?.sendBroadcast(setJobLocation)
                }
                if (index == adapterPosition){
                    linearLocation.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                } else {
                    linearLocation.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterLocationFilterJob.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemLocationFilterJobBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterLocationFilterJob.Holder, position: Int) {
        val job: DataItemHaina = jobList?.get(position)!!
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobList?.size!!
}