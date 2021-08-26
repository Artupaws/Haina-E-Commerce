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
import haina.ecommerce.databinding.ListItemSubforumBinding
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.forum.DataSubforum
import haina.ecommerce.model.forum.DataSubforumHotPost
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import java.util.ArrayList

class AdapterListSubforumFollow(
    val context: Context,
    private val listSubforum: ArrayList<SubforumData?>?,
    val itemAdapterCallback: ItemAdapterCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var index:Int = -1
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    companion object {
        var VIEW_TYPE = 1
    }

    inner class ViewHolderMySubforum(val binding: ListItemMyForumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: SubforumData, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                Glide.with(context).load(itemHaina.subforumImage).skipMemoryCache(true).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImageUser)
                tvNameUser.text = itemHaina.name
                val category = if (sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en") "${context.getString(R.string.category)} : ${itemHaina.category}" else "Category : ${itemHaina.categoryZh}"
                tvCategory.text = category
                val created = "${context.getString(R.string.created_at)} : ${dateFormat(itemHaina.createdAt)}"
                tvCreated.text = created
                val totalPost = "${context.getString(R.string.total_post)} : ${itemHaina.totalPost}"
                tvTotalPost.text = totalPost
                val totalPoster = "${context.getString(R.string.total_poster)} : ${itemHaina.totalPoster}"
                tvTotalContributor.text = totalPoster
                cvClick.setOnClickListener {
                    itemAdapterCallback.listSubforumClick(cvClick, itemHaina)
                }
            }
        }
    }

    inner class ViewHolderListSubforum(val binding:ListItemSubforumBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: SubforumData, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                Glide.with(context).load(itemHaina.subforumImage).skipMemoryCache(true).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImageUser)
                tvNameUser.text = itemHaina.name
                val category = if (sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en") "${context.getString(R.string.category)} : ${itemHaina.category}" else "Category : ${itemHaina.categoryZh}"
                tvCategory.text = category
                val created = "${context.getString(R.string.created_at)} : ${dateFormat(itemHaina.createdAt)}"
                tvCreated.text = created
                val totalPost = "${context.getString(R.string.total_post)} : ${itemHaina.totalPost}"
                tvTotalPost.text = totalPost
                val totalPoster = "${context.getString(R.string.total_poster)} : ${itemHaina.totalPoster}"
                tvTotalContributor.text = totalPoster
                ivImageAction.visibility = View.GONE
                cvClick.setOnClickListener { index = adapterPosition
                    notifyDataSetChanged()
                    itemAdapterCallback.listSubforumClick(cvClick, itemHaina)
                }
                if (index == adapterPosition){
                    relativeBackground.setBackgroundResource(R.drawable.background_line_corner_input_text_black)
                } else {
                    relativeBackground.setBackgroundResource(R.drawable.background_card_edge)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return if (viewType == 1) {
            ViewHolderMySubforum(
                ListItemMyForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            ViewHolderListSubforum(
                ListItemSubforumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo: SubforumData? = listSubforum?.get(position)
            if (VIEW_TYPE == 1){
                (holder as ViewHolderMySubforum).bind(photo!!, itemAdapterCallback)
            }
            else{
                (holder as ViewHolderListSubforum).bind(photo!!, itemAdapterCallback)
            }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int = listSubforum?.size!!

    interface ItemAdapterCallback {
        fun listSubforumClick(view: View, data: SubforumData)
    }

    fun add(data: List<SubforumData?>?) {
        listSubforum?.addAll(data!!)
        notifyItemRangeInserted((listSubforum?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listSubforum?.clear()
        notifyDataSetChanged()
    }

}