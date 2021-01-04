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
import haina.ecommerce.databinding.ListItemListDocumentBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterDocumentUser(val context: Context, private val listDocumentUser: List<DataDocumentUser?>?):
    RecyclerView.Adapter<AdapterDocumentUser.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemListDocumentBinding.bind(view)
        private var nameDocument:String? = null
        fun bind(itemHaina: DataDocumentUser){
            with(binding){
                nameDocument = (itemHaina.docs_name+itemHaina.id.toString())
                tvTitleDocument.text = nameDocument
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDocumentUser.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemListDocumentBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterDocumentUser.Holder, position: Int) {
        val photo: DataDocumentUser = listDocumentUser?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listDocumentUser?.size!!


}