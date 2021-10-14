package haina.ecommerce.adapter.forum

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants


class AdapterListHotPost(val context: Context,
                         private val listForum: java.util.ArrayList<DataItemHotPost?>?,
                         val itemAdapterCallback: ItemAdapterCallback,
                         val viewType:Int):
    RecyclerView.Adapter<AdapterListHotPost.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemForumBinding.bind(view)
        fun bind(itemHaina: DataItemHotPost, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                listParams = ArrayList()
                if (viewType == 1) tvOptionMenu.visibility = View.GONE else tvOptionMenu.visibility = View.VISIBLE
                tvNameUser.text = "Posted By ${itemHaina.author}"
                tvTitle.text = itemHaina.title
                tvLooks.text = itemHaina.viewCount.toString()
                tvNameSubforum.text = itemHaina.subforumData!!.name
                val totalUpvote = itemHaina.likeCount
                tvUpvote.text = totalUpvote.toString()
                tvComment.text = itemHaina.commentCount.toString()
                ivUpvote.isEnabled = itemHaina.author != sharedPref.getValueString(Constants.PREF_USERNAME)
                ivUpvote.isChecked = itemHaina.upvoted == true
                ivUpvote.setOnCheckedChangeListener { _, isChecked ->
                    if (!isChecked){
                        itemAdapterCallback.listForumClick(ivUpvote, false, itemHaina)
                        val likeCountParams = tvUpvote.text.toString().toInt().minus(1)
                        tvUpvote.text = likeCountParams.toString()
                    } else {
                        itemAdapterCallback.listForumClick(ivUpvote, true, itemHaina)
                        val likeCountParams = tvUpvote.text.toString().toInt().plus(1)
                        tvUpvote.text = likeCountParams.toString()
                    }
                }
                tvOptionMenu.setOnClickListener {
                    val popup = PopupMenu(context, tvOptionMenu)
                    popup.inflate(R.menu.menu_option_delete_item)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {

                        }
                        false
                    }
                    popup.show()
                }
                tvDate.text = helper.getTimeAgo(itemHaina.createdAt)
                Glide.with(context).load(itemHaina.subforumData.subforumImage).into(ivImageSubforum)
                if (itemHaina.images != null){

                    llImageForum.removeAllViewsInLayout()
                    val inflater = LayoutInflater.from(context)

                    when(itemHaina.images.size){
                        1 -> {
                            val imageview = LayoutOneImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView1)
                            llImageForum.addView(imageview.root)
                        }
                        2 -> {
                            val imageview = LayoutTwoImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView1)
                            Glide.with(context).load(itemHaina.images[1]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView2)
                            llImageForum.addView(imageview.root)
                        }

                        3 -> {
                            val imageview = LayoutThreeImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView1)
                            Glide.with(context).load(itemHaina.images[1]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView2)
                            Glide.with(context).load(itemHaina.images[2]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView3)
                            llImageForum.addView(imageview.root)
                        }

                        4 -> {
                            val imageview = LayoutFourImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView1)
                            Glide.with(context).load(itemHaina.images[1]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView2)
                            Glide.with(context).load(itemHaina.images[2]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView3)
                            Glide.with(context).load(itemHaina.images[3]!!.path).placeholder(R.drawable.ps5).into(imageview.imageView4)
                            llImageForum.addView(imageview.root)
                        }
                    }
                }

                relativeClick.setOnClickListener {
                    itemAdapterCallback.listForumClick(relativeClick, false, itemHaina)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHotPost.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemForumBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHotPost.Holder, position: Int) {
        val photo: DataItemHotPost = listForum?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listForum?.size!!

    interface ItemAdapterCallback{
        fun listForumClick(view: View, isChecked:Boolean, data:DataItemHotPost)
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