package haina.ecommerce.adapter.companycatalog.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCompanyMediaBinding

class CompanyDataMediaAdapter() : RecyclerView.Adapter<CompanyDataMediaAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val binding = ListItemCompanyMediaBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.binding.ivMedia.context).load(R.drawable.ps5).into(holder.binding.ivMedia)
    }

    override fun getItemCount(): Int = 3

    inner class Holder(var binding: ListItemCompanyMediaBinding) : RecyclerView.ViewHolder(binding.root){

    }
}