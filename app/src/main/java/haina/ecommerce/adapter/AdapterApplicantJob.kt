package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemSellingBinding
import haina.ecommerce.databinding.ListItemUserApplicantBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.JobapplicantItem
import haina.ecommerce.view.posting.detailvacancy.DetailPostingJobActivity

class AdapterApplicantJob(val context: Context, private val listJob: List<JobapplicantItem?>?): RecyclerView.Adapter<AdapterApplicantJob.Holder>(){


    inner class Holder (view:View): RecyclerView.ViewHolder(view){
        private val binding = ListItemUserApplicantBinding.bind(view)
        fun bin(item: JobapplicantItem?){
            with(binding){
              tvFullname.text = item?.fullname
                tvPhone.text = item?.phone
                tvEmail.text = item?.email
                tvGender.text = item?.gender
                Glide.with(context).load(item?.photo).skipMemoryCache(false).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImagePost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemUserApplicantBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val selling: JobapplicantItem? = listJob?.get(position)!!
        holder.bin(selling)
    }

    override fun getItemCount(): Int = listJob?.size!!
}