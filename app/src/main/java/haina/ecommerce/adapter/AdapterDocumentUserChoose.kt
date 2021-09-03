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
import haina.ecommerce.databinding.ListItemListDocumentBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity
import java.util.ArrayList


class AdapterDocumentUserChoose(val context: Context,
                                private val listDocumentUser: List<DataDocumentUser?>?,
                                private val adapterResumeClick:AdapterResumeClick):
    RecyclerView.Adapter<AdapterDocumentUserChoose.Holder>() {

    private var index:Int = -1
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemListDocumentBinding.bind(view)
        private var nameDocument:String? = null
        fun bind(itemHaina: DataDocumentUser, adapterResumeClick: AdapterResumeClick){
            with(binding){
                nameDocument = (itemHaina.docs_name)
                tvTitleDocument.text = nameDocument
                ivActionDelete.visibility = View.GONE
                cardView2.setOnClickListener {
                    index = adapterPosition
                    notifyDataSetChanged()
                    adapterResumeClick.listResumeClicked(cardView2, itemHaina)
//                    val sendIdDocument = Intent("idDocument")
//                        .putExtra("resume", itemHaina.id)
//                    broadcaster?.sendBroadcast(sendIdDocument)
                }
                if (index == adapterPosition){
                    clClick.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                }else {
                    clClick.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDocumentUserChoose.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemListDocumentBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterDocumentUserChoose.Holder, position: Int) {
        val photo: DataDocumentUser = listDocumentUser?.get(position)!!
        holder.bind(photo, adapterResumeClick)
    }

    fun addResume(data:List<DataDocumentUser?>?){
        data?.let { (listDocumentUser as ArrayList<DataDocumentUser?>?)?.addAll(it) }
        notifyItemRangeInserted((listDocumentUser?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear(){
        (listDocumentUser as ArrayList<DataDocumentUser?>?)?.clear()
        notifyDataSetChanged()
    }

    interface AdapterResumeClick{
        fun listResumeClicked(view:View, data:DataDocumentUser)
    }

    override fun getItemCount(): Int = listDocumentUser?.size!!


}