package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterAddressCompany(val context: Context, private val listAddressCompanies: List<AddressItemCompany?>?):
    RecyclerView.Adapter<AdapterAddressCompany.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemAddressCompanyBinding.bind(view)
        fun bind(itemHaina: AddressItemCompany){
            with(binding){
                tvAddress.text = itemHaina.address
                tvCity.text = itemHaina.city
                Log.d("photo", itemHaina.address.toString())
                ivAction.setOnClickListener {
                    val popup = PopupMenu(context, ivAction)
                    popup.inflate(haina.ecommerce.R.menu.menu_option_address_company)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            haina.ecommerce.R.id.action_edit ->{
                                val intent = Intent(context, AddAddressCompanyActivity::class.java)
                                intent.putExtra("address", itemHaina.address)
                                intent.putExtra("idCompany", itemHaina.id)
                                intent.putExtra("idLocation", itemHaina.idCity)
                                intent.putExtra("nameLocation", itemHaina.city)
                                context.startActivity(intent)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }
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