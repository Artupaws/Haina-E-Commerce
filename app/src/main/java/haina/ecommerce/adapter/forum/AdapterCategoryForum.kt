package haina.ecommerce.adapter.forum

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemCategoryForumBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.forum.DataCategoryForum
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterCategoryForum(val context: Context,
                           private val listCategoryForum: List<DataCategoryForum?>?,
val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterCategoryForum.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var broadcaster:LocalBroadcastManager
    private var index:Int = 0
    private var responseId:Int =0

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCategoryForumBinding.bind(view)
        fun bind(itemHaina: DataCategoryForum, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                if (sharedPref.getValueString(Constants.LANGUAGE_APP) == "en"){
                    tvNameCategory.text = itemHaina.name
                } else {
                    tvNameCategory.text = itemHaina.nameZh
                }
                cvClick.setOnClickListener {
                    index = adapterPosition
                    notifyDataSetChanged()
                    itemAdapterCallback.categoryForumClick(cvClick, itemHaina)
                }

                if (index == adapterPosition){
                    relativeCategory.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                } else {
                    relativeCategory.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategoryForum.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCategoryForumBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCategoryForum.Holder, position: Int) {
        val photo: DataCategoryForum = listCategoryForum?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listCategoryForum?.size!!

    interface ItemAdapterCallback{
        fun categoryForumClick(view: View, data:DataCategoryForum)
    }

}