package haina.ecommerce.adapter.forum

import android.annotation.SuppressLint
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
import haina.ecommerce.databinding.ListItemUserCommentBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.CommentsItem
import haina.ecommerce.model.forum.DataAllCommentUser
import haina.ecommerce.model.forum.DataComment
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterListUserComment(val context: Context,
                         private val listComment: java.util.ArrayList<CommentsItem?>?,
                         val itemAdapterCallback: ItemAdapterCallback,
                         val viewType:Int):
    RecyclerView.Adapter<AdapterListUserComment.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemUserCommentBinding.bind(view)
        fun bind(itemHaina: CommentsItem, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                listParams = ArrayList()
                tvNameSubforum.text = "${context.getString(R.string.posted_by)} ${itemHaina.postData!!.subforumData!!.name}"
                tvTitle.text = itemHaina.postData!!.title
                tvContent.text = itemHaina.content
                splitterPost.visibility = View.GONE

                var datePost = helper.getTimeAgo(itemHaina.createdAt)
                if(sharedPref.getValueString(Constants.LANGUAGE_APP) == "zh"){
                    datePost = datePost.replace("d", "天")
                    datePost = datePost.replace("h", "小时")
                }


                tvDate.text = datePost

                Glide.with(context).load(itemHaina.postData.subforumData!!.subforumImage).into(ivImageSubforum)

                relativeClick.setOnClickListener {
                    itemAdapterCallback.commentClick(relativeClick, false, itemHaina)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListUserComment.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemUserCommentBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListUserComment.Holder, position: Int) {
        val photo: CommentsItem = listComment?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listComment?.size!!

    interface ItemAdapterCallback{
        fun commentClick(view: View, isChecked:Boolean, data:CommentsItem)
    }

    fun add(data:List<CommentsItem?>?){
        listComment?.addAll(data!!)
        notifyItemRangeInserted((listComment?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listComment?.clear()
        notifyDataSetChanged()
    }

}