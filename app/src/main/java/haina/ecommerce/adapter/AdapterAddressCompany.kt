package haina.ecommerce.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemPhotoCompanyBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.PhotoItemDataCompany

class AdapterAddressCompany(val context: Context, private val listAddressCompanies: List<AddressItemCompany?>?):
    RecyclerView.Adapter<AdapterAddressCompany.Holder>() {

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemAddressCompanyBinding.bind(view)
        fun bind(item: AddressItemCompany){
            with(binding){
                tvAddress.text = item.address
                tvCity.text = item.city
                Log.d("photo", item.address.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAddressCompany.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAddressCompanyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAddressCompany.Holder, position: Int) {
        val photo: AddressItemCompany = listAddressCompanies?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listAddressCompanies?.size!!
}