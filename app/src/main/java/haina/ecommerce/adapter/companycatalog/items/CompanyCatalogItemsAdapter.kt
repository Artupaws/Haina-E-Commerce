package haina.ecommerce.adapter.companycatalog.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCompanyCatalogItemsBinding


class CompanyCatalogItemsAdapter(val callback: ItemAdapterCallback) : RecyclerView.Adapter<CompanyCatalogItemsAdapter.Holder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val binding = ListItemCompanyCatalogItemsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(holder.binding.ivMedia.context).load(R.drawable.ps5).into(holder.binding.ivMedia)
        holder.binding.tvItemName.text = "PS5 Base"
        holder.binding.tvItemCategory.text = "Electronics"
        holder.binding.tvItemCatalog.text = "Gaming"
        holder.binding.tvPrice.text = "Rp 4.900.000"

        holder.binding.cvItems.setOnClickListener {
            callback.itemOnClick()
        }
    }

    override fun getItemCount(): Int = 10

    inner class Holder(var binding: ListItemCompanyCatalogItemsBinding) : RecyclerView.ViewHolder(binding.root){

    }

    interface ItemAdapterCallback{
        fun itemOnClick()
    }
}