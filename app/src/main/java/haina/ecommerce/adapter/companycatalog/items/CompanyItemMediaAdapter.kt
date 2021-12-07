package haina.ecommerce.adapter.companycatalog.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCompanyItemMediaBinding

class CompanyItemMediaAdapter() : RecyclerView.Adapter<CompanyItemMediaAdapter.Holder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val binding = ListItemCompanyItemMediaBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount() = 3

    inner class Holder(var binding: ListItemCompanyItemMediaBinding) : RecyclerView.ViewHolder(binding.root){

    }

}