package haina.ecommerce.adapter.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemMyForumBinding
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.forum.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import java.util.ArrayList

class AdapterListBan(
    val context: Context,
    private val modList: ArrayList<DataItemBan?>?,
    val itemAdapterCallback: ItemAdapterCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    inner class ViewHolderMySubforum(val binding: ListItemMyForumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataItemBan, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                Glide.with(context).load(itemHaina.subforumImage).skipMemoryCache(true).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImageUser)
                tvNameUser.text = itemHaina.name
                val category = if (sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en") "${context.getString(R.string.category)} : ${itemHaina.category}" else "Category : ${itemHaina.categoryZh}"
                tvCategory.text = category
                val created = "${context.getString(R.string.created_at)} : ${dateFormat(itemHaina.createdAt)}"
                tvCreated.text = created
                val banReason = "${context.getString(R.string.ban_reason)} : ${itemHaina.banReason}"
                tvTotalPost.text = banReason
                val totalPoster = "${context.getString(R.string.total_poster)} : ${itemHaina.totalPoster}"
                tvTotalContributor.text = totalPoster
                cvClick.setOnClickListener {
                    itemAdapterCallback.listBanClick(cvClick, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return ViewHolderMySubforum(ListItemMyForumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo: DataItemBan? = modList?.get(position)
        (holder as ViewHolderMySubforum).bind(photo!!, itemAdapterCallback)
    }

    override fun getItemCount(): Int = modList?.size!!

    interface ItemAdapterCallback {
        fun listBanClick(view: View, data: DataItemBan)
    }

    fun add(data: List<DataItemBan?>?) {
        modList?.addAll(data!!)
        notifyItemRangeInserted((modList?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        modList?.clear()
        notifyDataSetChanged()
    }

}