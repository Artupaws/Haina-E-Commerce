package haina.ecommerce.adapter.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemModeratorForumBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterListBannedUser(val context: Context,
                                 private val listBannedUser: java.util.ArrayList<DataBannedUser?>?,
                                 val itemAdapterCallback: ItemAdapterCallback,
                                 val viewType:Int):
    RecyclerView.Adapter<AdapterListBannedUser.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemModeratorForumBinding.bind(view)
        fun bind(itemHaina: DataBannedUser, itemAdapterCallback: ItemAdapterCallback, viewType: Int){
            with(binding){
                listParams = ArrayList()
                Glide.with(context).load(itemHaina.user!!.photo).into(ivImage)
                tvName.text = itemHaina.user!!.username
                tvMemberSince.text = "Banned By ${itemHaina.mod!!.username}"
                tvRole.text = helper.getTimeAgo(itemHaina.createdAt)
                btnRemoveMod.setOnClickListener {
                    itemAdapterCallback.removeBanned(itemHaina)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListBannedUser.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemModeratorForumBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListBannedUser.Holder, position: Int) {
        val photo: DataBannedUser = listBannedUser?.get(position)!!
        holder.bind(photo, itemAdapterCallback, viewType)
    }

    override fun getItemCount(): Int = listBannedUser?.size!!

    interface ItemAdapterCallback{
        fun removeBanned(data:DataBannedUser)
    }

    fun add(data:List<DataBannedUser?>?){
        listBannedUser?.addAll(data!!)
        notifyItemRangeInserted((listBannedUser?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listBannedUser?.clear()
        notifyDataSetChanged()
    }
}