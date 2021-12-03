package haina.ecommerce.adapter.companycatalog.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCompanyOverviewAdBinding

class CompanyPromotedItemsAdapter(var callback: ItemAdapterCallback) : RecyclerView.Adapter<CompanyPromotedItemsAdapter.Holder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CompanyPromotedItemsAdapter.Holder {
        val binding = ListItemCompanyOverviewAdBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: CompanyPromotedItemsAdapter.Holder, position: Int) {
        Glide.with(holder.binding.ivAdItemImage.context).load(R.drawable.ps5).into(holder.binding.ivAdItemImage)

        holder.binding.tvAdItemName.text = "PS5"
        holder.binding.tvAdItemPrice.text = "Rp. 4.000.000"

        holder.binding.cvPromoItem.setOnClickListener{
            callback.promotedItemOnClick()
        }
    }

    override fun getItemCount(): Int = 3

    inner class Holder(val binding: ListItemCompanyOverviewAdBinding) : RecyclerView.ViewHolder(binding.root){

    }

    interface ItemAdapterCallback{
        fun promotedItemOnClick()
    }
}