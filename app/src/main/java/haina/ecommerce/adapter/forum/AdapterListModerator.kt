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
import haina.ecommerce.databinding.ListItemForumBinding
import haina.ecommerce.databinding.ListItemForumProfileBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.model.forum.Moderator
import haina.ecommerce.model.forum.ThreadsItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterListModerator(val context: Context,
                        private val listModerator: java.util.ArrayList<Moderator?>?,
                        val itemAdapterCallback: ItemAdapterCallback,
                        val viewType:Int):
    RecyclerView.Adapter<AdapterListModerator.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemForumProfileBinding.bind(view)
        fun bind(itemHaina: Moderator, itemAdapterCallback: ItemAdapterCallback, viewType: Int){
            with(binding){
                listParams = ArrayList()
                Glide.with(context).load(itemHaina.photo).into(ivImage)
                tvName.text = itemHaina.username
                tvMemberSince.text = "Member Since ${itemHaina.memberSince}"
                tvRole.text = itemHaina.role
                relativeProfile.setOnClickListener {
                    itemAdapterCallback.listProfileClick(relativeProfile, false, itemHaina)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListModerator.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemForumProfileBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListModerator.Holder, position: Int) {
        val photo: Moderator = listModerator?.get(position)!!
        holder.bind(photo, itemAdapterCallback, viewType)
    }

    override fun getItemCount(): Int = listModerator?.size!!

    interface ItemAdapterCallback{
        fun listProfileClick(view: View, isChecked:Boolean, data:Moderator)
    }

    fun add(data:List<Moderator?>?){
        listModerator?.addAll(data!!)
        notifyItemRangeInserted((listModerator?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listModerator?.clear()
        notifyDataSetChanged()
    }
}