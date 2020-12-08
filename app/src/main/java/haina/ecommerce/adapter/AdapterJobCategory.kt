package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAutotextcompleteBinding
import haina.ecommerce.model.DataItemHaina

class AdapterJobCategory(private val context: Context, private val jobList: List<DataItemHaina?>?): RecyclerView.Adapter<AdapterJobCategory.Holder>(){

    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemAutotextcompleteBinding.bind(itemView)
        fun bind(itemHaina: DataItemHaina){
            with(binding){
                tvName.text = itemHaina.name
                tvName.setOnClickListener {
                    val setJobCategory = Intent("jobCategory")
                            .putExtra("Category", tvName.text)
                    broadcaster?.sendBroadcast(setJobCategory)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterJobCategory.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAutotextcompleteBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterJobCategory.Holder, position: Int) {
        val job: DataItemHaina = jobList?.get(position)!!
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobList?.size!!
}