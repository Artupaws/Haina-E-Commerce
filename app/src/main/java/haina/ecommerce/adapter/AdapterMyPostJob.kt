package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemSellingBinding
import haina.ecommerce.model.DataMyPost
import haina.ecommerce.model.Selling

class AdapterMyPostJob(val context: Context, private val listJob: List<DataMyPost?>?): RecyclerView.Adapter<AdapterMyPostJob.Holder>(){

    inner class Holder (view:View): RecyclerView.ViewHolder(view){
        private val binding = ListItemSellingBinding.bind(view)
        fun bin(item: DataMyPost?){
            with(binding){
                tvTitlePost.text = item?.title
                tvStatus.text = item?.status
                tvDatePost.text = item?.photoUrl.toString()
                Glide.with(context).load(item?.photoUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivImagePost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSellingBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val selling: DataMyPost? = listJob?.get(position)!!
        holder.bin(selling)
    }

    override fun getItemCount(): Int = listJob?.size!!
}