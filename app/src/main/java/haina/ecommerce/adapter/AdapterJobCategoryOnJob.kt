package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemJobCategoryBinding
import haina.ecommerce.model.DataItemHaina

class AdapterJobCategoryOnJob(private val context: Context, private val jobList: MutableList<DataItemHaina?>?): RecyclerView.Adapter<AdapterJobCategoryOnJob.Holder>(){

    private var index:Int = 0
    private var responseId:Int =0
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemJobCategoryBinding.bind(itemView)
        fun bind(itemHaina: DataItemHaina){
            with(binding){
                    tvTitleCategoryJob.text = itemHaina.displayName
                linearClick.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    responseId = if(index==0){ 0
                    } else {
                        itemHaina.id!!.toInt()
                    }
                    val setJobCategory = Intent("jobCategoryFilter")
                        .putExtra("idCategoryJobFilter", responseId)
                    broadcaster?.sendBroadcast(setJobCategory)
                }

                if (index == adapterPosition){
                    linearCategory.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                } else {
                    linearCategory.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterJobCategoryOnJob.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemJobCategoryBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterJobCategoryOnJob.Holder, position: Int) {
        val job: DataItemHaina = jobList?.get(position)!!
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobList?.size!!
}