package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemJobCategoryBinding
import haina.ecommerce.model.DataItemHaina

class AdapterJobCategoryOnJob(private val context: Context, private val jobList: List<DataItemHaina?>?): RecyclerView.Adapter<AdapterJobCategoryOnJob.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemJobCategoryBinding.bind(itemView)
        fun bind(itemHaina: DataItemHaina){
            with(binding){
                    tvTitleCategoryJob.text = itemHaina.displayName
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterJobCategoryOnJob.Holder {
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