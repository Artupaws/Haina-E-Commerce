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
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterListMyPost(val context: Context,
                        private val listForum: java.util.ArrayList<DataItemHotPost?>?,
                        val itemAdapterCallback: ItemAdapterCallback,
                        val viewType:Int):
    RecyclerView.Adapter<AdapterListMyPost.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemForumBinding.bind(view)
        fun bind(itemHaina: DataItemHotPost, itemAdapterCallback: ItemAdapterCallback, viewType: Int){
            with(binding){
                listParams = ArrayList()
                if (viewType == 1) tvOptionMenu.visibility = View.GONE else tvOptionMenu.visibility = View.VISIBLE
                tvNameSubforum.text = itemHaina.author
                tvTitle.text = itemHaina.title
                tvLooks.text = itemHaina.viewCount.toString()
                tvUpvote.text = itemHaina.likeCount.toString()
                tvComment.text = itemHaina.commentCount.toString()
                tvDate.text = helper.getTimeAgo(itemHaina.createdAt)
                ivUpvote.isChecked = itemHaina.upvoted == true
                ivUpvote.isEnabled = sharedPref.getValueString(Constants.PREF_USERNAME) != itemHaina.author
                ivUpvote.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked){
                        itemAdapterCallback.listMyPostClick(ivUpvote, true, itemHaina)
                        tvUpvote.text = "${tvUpvote.text.toString().toInt().plus(1)}"
                    } else {
                        itemAdapterCallback.listMyPostClick(ivUpvote, false, itemHaina)
                        tvUpvote.text = "${tvUpvote.text.toString().toInt().minus(1)}"
                    }
                }
                tvOptionMenu.setOnClickListener {
                    itemAdapterCallback.deleteListMyPost(tvOptionMenu, listForum, adapterPosition, itemHaina.id!!)
                }

                if (itemHaina.images != null){
                    for (i in itemHaina.images) {
                        i?.path?.let { listParams.add(it) }
                        vpImageProperty.pageCount = listParams.size
                    }
                    imagesListener = ImageListener { _, imageView ->
                        Glide.with(context).load(listParams[0]).placeholder(R.drawable.ps5).into(imageView)
                    }
                    vpImageProperty.setImageListener(imagesListener)
                    vpImageProperty.setImageListener(imagesListener)
                }
                if(itemHaina.images.isNullOrEmpty()) {
                    listParams.add("https://hainaservice.com/storage/empty.jpg")
                    vpImageProperty.pageCount = listParams.size
                    imagesListener = ImageListener { _, imageView ->
                        Glide.with(context).load(listParams[0]).placeholder(R.drawable.ps5).into(imageView)
                    }
                    vpImageProperty.setImageListener(imagesListener)
                    vpImageProperty.setImageListener(imagesListener)
                }
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listMyPostClick(relativeClick, false, itemHaina)
                }
                Glide.with(context).load(itemHaina.authorPhoto).into(ivImageSubforum)

                splitterPost.visibility = View.GONE
                tvNameUser.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListMyPost.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemForumBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListMyPost.Holder, position: Int) {
        val photo: DataItemHotPost = listForum?.get(position)!!
        holder.bind(photo, itemAdapterCallback, viewType)
    }

    override fun getItemCount(): Int = listForum?.size!!

    interface ItemAdapterCallback{
        fun listMyPostClick(view: View, isChecked:Boolean, data:DataItemHotPost)
        fun deleteListMyPost(view: View, data:java.util.ArrayList<DataItemHotPost?>?, adapterPosition:Int, postId:Int)
    }

    fun add(data:List<DataItemHotPost?>?){
        listForum?.addAll(data!!)
        notifyItemRangeInserted((listForum?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listForum?.clear()
        notifyDataSetChanged()
    }
}