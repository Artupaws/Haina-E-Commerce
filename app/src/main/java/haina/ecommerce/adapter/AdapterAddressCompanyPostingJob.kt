package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterAddressCompanyPostingJob(val context: Context, private val listAddressCompanies: List<AddressItemCompany?>?):
    RecyclerView.Adapter<AdapterAddressCompanyPostingJob.Holder>() {

    private var index:Int = -1
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemAddressCompanyBinding.bind(view)
        fun bind(itemHaina: AddressItemCompany){
            with(binding){
                tvAddress.text = itemHaina.address
                tvCity.text = itemHaina.city
                Log.d("photo", itemHaina.address.toString())
                ivAction.visibility = View.GONE
                cvClicked.setOnClickListener {
                    index = adapterPosition
                    notifyDataSetChanged()
                    val sendIdAddressCompany = Intent("addressCompany")
                            .putExtra("idAddress", itemHaina.id)
                    broadcaster?.sendBroadcast(sendIdAddressCompany)
                }

                if (index == adapterPosition){
                    cvClicked.setBackgroundColor(context.resources.getColor(R.color.black))
                    tvCity.setTextColor(context.resources.getColor(R.color.white))
                    tvAddress.setTextColor(context.resources.getColor(R.color.white))
                } else {
                    cvClicked.setBackgroundColor(context.resources.getColor(R.color.white))
                    tvCity.setTextColor(context.resources.getColor(R.color.black))
                    tvAddress.setTextColor(context.resources.getColor(R.color.black))
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAddressCompanyPostingJob.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAddressCompanyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAddressCompanyPostingJob.Holder, position: Int) {
        val photo: AddressItemCompany = listAddressCompanies?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listAddressCompanies?.size!!


}