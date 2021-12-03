package haina.ecommerce.adapter.companycatalog.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCompanyCatalogBinding

class CompanyCatalogListAdapter(var callback: ItemAdapterCallback) : RecyclerView.Adapter<CompanyCatalogListAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val binding = ListItemCompanyCatalogBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: CompanyCatalogListAdapter.Holder, position: Int) {
        Glide.with(holder.binding.ivMedia.context).load(R.drawable.ps5).placeholder(R.drawable.skeleton_image).into(holder.binding.ivMedia)

        holder.binding.tvNameCatalog.text = "Gaming"
        holder.binding.tvStartingPrice.text = "Rp 4.900.000"
        holder.binding.tvItemCount.text = "10"

        holder.binding.cvCatalogs.setOnClickListener {
            callback.catalogOnClick()
        }
    }

    override fun getItemCount(): Int = 10

    inner class Holder(var binding: ListItemCompanyCatalogBinding) : RecyclerView.ViewHolder(binding.root){

    }

    interface ItemAdapterCallback{
        fun catalogOnClick()
    }

}