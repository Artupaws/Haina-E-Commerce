package haina.ecommerce.adapter.companycatalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCompanyCategoryBinding

class CompanyDashboardAdapter() : RecyclerView.Adapter<CompanyDashboardAdapter.Holder>() {

    private lateinit var onItemClickCallback : ItemAdapterCallback

    fun setOnItemCallback(onItemClickCallback: ItemAdapterCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val binding = ListItemCompanyCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.tvCompanyCategoryName.text = "Category"
        Glide.with(holder.binding.ivCompanyCategory.context).load(R.drawable.ps5).into(holder.binding.ivCompanyCategory)

        holder.itemView.setOnClickListener {
            onItemClickCallback.categoryOnClick()
        }
    }

    override fun getItemCount(): Int = 10

    inner class Holder(var binding: ListItemCompanyCategoryBinding) : RecyclerView.ViewHolder(binding.root){

    }

    interface ItemAdapterCallback{
        fun categoryOnClick()
    }

}