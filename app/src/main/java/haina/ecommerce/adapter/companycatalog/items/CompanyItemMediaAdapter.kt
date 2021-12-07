package haina.ecommerce.adapter.companycatalog.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCompanyMediaBinding

class CompanyItemMediaAdapter() : RecyclerView.Adapter<CompanyItemMediaAdapter.Holder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val binding = ListItemCompanyMediaBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount() = 3

    inner class Holder(var binding: ListItemCompanyMediaBinding) : RecyclerView.ViewHolder(binding.root){

    }

}