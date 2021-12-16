package haina.ecommerce.adapter.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.ImagesItem
import haina.ecommerce.model.forum.ThreadsItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterListAllThreads(val context: Context,

                            private val listForum: java.util.ArrayList<ThreadsItem?>?,
                            val itemAdapterCallback: ItemAdapterCallback,
                            val viewType:Int):
    RecyclerView.Adapter<AdapterListAllThreads.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemForumBinding.bind(view)
        fun bind(itemHaina: ThreadsItem, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                listParams = ArrayList()
                tvNameUser.text = "${context.getString(R.string.posted_by)} ${itemHaina.author}"
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
                        itemAdapterCallback.listAllThreadsClick(ivUpvote, false, itemHaina)
                        val likeCountParams = tvUpvote.text.toString().toInt().minus(1)
                        tvUpvote.text = likeCountParams.toString()
                    } else {
                        itemAdapterCallback.listAllThreadsClick(ivUpvote, true, itemHaina)
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

                var datePost = helper.getTimeAgo(itemHaina.createdAt)
                Timber.e("Coba: " + itemHaina.createdAt.toString())
                if(sharedPref.getValueString(Constants.LANGUAGE_APP) == "zh"){
                    datePost = datePost.replace("d", "天")
                    datePost = datePost.replace("h", "小时")
                }

                tvDate.text = datePost

                Glide.with(context).load(itemHaina.subforumData.subforumImage).into(ivImageSubforum)

                if(!itemHaina.images.isNullOrEmpty()){
                    llImageForum.removeAllViewsInLayout()
                    val inflater = LayoutInflater.from(context)

                    when(itemHaina.images.size){
                        1 -> {
                            val imageview = LayoutOneImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            imageview.imageView1.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,0)
                            }
                            llImageForum.addView(imageview.root)
                        }
                        2 -> {
                            val imageview = LayoutTwoImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            Glide.with(context).load(itemHaina.images[1]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                            imageview.imageView1.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,0)
                            }
                            imageview.imageView2.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,1)
                            }
                            llImageForum.addView(imageview.root)
                        }

                        3 -> {
                            val imageview = LayoutThreeImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            Glide.with(context).load(itemHaina.images[1]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                            Glide.with(context).load(itemHaina.images[2]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView3)
                            imageview.imageView1.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,0)
                            }
                            imageview.imageView2.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,1)
                            }
                            imageview.imageView3.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,2)
                            }
                            llImageForum.addView(imageview.root)
                        }

                        4 -> {
                            val imageview = LayoutFourImageForumBinding.inflate(inflater)
                            Glide.with(context).load(itemHaina.images[0]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            Glide.with(context).load(itemHaina.images[1]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                            Glide.with(context).load(itemHaina.images[2]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView3)
                            Glide.with(context).load(itemHaina.images[3]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView4)
                            imageview.imageView1.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,0)
                            }
                            imageview.imageView2.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,1)
                            }
                            imageview.imageView3.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,2)
                            }
                            imageview.imageView4.setOnClickListener {
                                itemAdapterCallback.detailPhoto(itemHaina.images,3)
                            }
                            llImageForum.addView(imageview.root)
                        }
                    }
                }
                relativeClick.setOnClickListener {
                    itemAdapterCallback.listAllThreadsClick(relativeClick, false, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListAllThreads.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemForumBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListAllThreads.Holder, position: Int) {
        val photo: ThreadsItem = listForum?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listForum?.size!!

    interface ItemAdapterCallback{
        fun listAllThreadsClick(view: View, isChecked:Boolean, data:ThreadsItem)
        fun detailPhoto(listImage: List<ImagesItem?>?,position:Int)
    }

    fun add(data:List<ThreadsItem?>?){
        listForum?.addAll(data!!)
        notifyItemRangeInserted((listForum?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listForum?.clear()
        notifyDataSetChanged()
    }

}