package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemSellingBinding
import haina.ecommerce.databinding.ListItemSubmitApplicationBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataJobApplication
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.view.posting.detailvacancy.DetailPostingJobActivity

class AdapterHistorySubmitJob(val context: Context, private val listJob: List<DataJobApplication?>?): RecyclerView.Adapter<AdapterHistorySubmitJob.Holder>(){

    inner class Holder (view:View): RecyclerView.ViewHolder(view){
        private val binding = ListItemSubmitApplicationBinding.bind(view)
        fun bin(item: DataJobApplication?){
            with(binding) {
                tvTitle.text = item?.jobtitle
                tvNameCompany.text = item?.company?.name
                tvAppliedDate.text = item?.date
                tvStatus.text = item?.status
                Glide.with(context).load(item?.company?.iconUrl).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivImagePost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSubmitApplicationBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val selling: DataJobApplication? = listJob?.get(position)!!
        holder.bin(selling)
    }

    override fun getItemCount(): Int = listJob?.size!!
}