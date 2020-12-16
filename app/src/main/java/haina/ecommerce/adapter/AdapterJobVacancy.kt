package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemJobCategoryBinding
import haina.ecommerce.databinding.ListItemJobVacancyBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob

class AdapterJobVacancy(private val context: Context, private val jobList: List<DataItemJob?>?): RecyclerView.Adapter<AdapterJobVacancy.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemJobVacancyBinding.bind(itemView)
        fun bind(itemHaina: DataItemJob){
            with(binding){
                    tvTitleJob.text = itemHaina.title
                tvDatePublish.text = itemHaina.date
                Glide.with(context).load(itemHaina.photoUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivImageCompany)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterJobVacancy.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemJobVacancyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterJobVacancy.Holder, position: Int) {
        val job: DataItemJob = jobList?.get(position)!!
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobList?.size!!
}