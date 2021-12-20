package haina.ecommerce.adapter.forum

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCommentForumBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.DataComment
import haina.ecommerce.model.forum.DataForum
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants


class AdapterListComment(val context: Context,
                         private val listComment: java.util.ArrayList<DataComment?>?,
                         val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterListComment.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private val helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCommentForumBinding.bind(view)
        fun bind(itemHaina: DataComment, itemAdapterCallback: ItemAdapterCallback){
            with(binding) {
                Glide.with(context).load(itemHaina.userPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(ivImageUser)
                tvComment.text = itemHaina.content
                tvUsername.text = itemHaina.username
                val memberSince = "${context?.getString(R.string.member_since)} ${itemHaina.memberSince}"
                tvMemberSince.text = helper.dateTimeFormat(itemHaina.createdAt)
                if (!itemHaina.mod?.contains("none")!!){
                    binding.tvMod.text = itemHaina.mod
                    if(itemHaina.mod?.equals("banned")){
                        binding.tvMod.setBackgroundColor(Color.rgb(194, 76, 76))
                        binding.tvMod.setTextColor(Color.WHITE)
                    }
                } else{
                    binding.tvMod.visibility = View.GONE
                }
                tvOptionMenu.setOnClickListener {
                    itemAdapterCallback.listCommentClick(tvOptionMenu, listComment, adapterPosition, itemHaina)
                }
                ivImageUser.setOnClickListener {
                    itemAdapterCallback.listCommentClick(ivImageUser, listComment, adapterPosition, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListComment.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCommentForumBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListComment.Holder, position: Int) {
        val photo: DataComment = listComment?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listComment?.size!!

    interface ItemAdapterCallback{
        fun listCommentClick(view: View, listComment:java.util.ArrayList<DataComment?>?, adapterPosition:Int, data:DataComment)
    }

    fun add(data:List<DataComment?>?){
        listComment?.addAll(data!!)
        notifyItemRangeInserted((listComment?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listComment?.clear()
        notifyDataSetChanged()
    }

}