package haina.ecommerce.adapter.forum

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.databinding.ListItemInputImageBinding
import haina.ecommerce.room.roomvideopost.VideoPostData

class AdapterInputVideos(private val context: Context, private val videoList: MutableList<VideoPostData>?,
                         val inputVideoClick: InputVideoClick): RecyclerView.Adapter<AdapterInputVideos.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemInputImageBinding.bind(itemView)
        fun bind(itemHaina: VideoPostData, inputVideoClick: InputVideoClick){
            with(binding) {
                if (itemHaina.id == -1){
                    ivAction.visibility = View.GONE
                } else {
                    ivAction.visibility = View.VISIBLE
                }
                if (itemHaina.video.contains("&#xf067;")) {
                    val icon = HtmlCompat.fromHtml(itemHaina.video, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    faIcon.text = icon
                    ivImages.visibility = View.GONE
                    faIcon.visibility = View.VISIBLE
                }else {
                    val uri = Uri.parse(itemHaina.video)
                    ivImages.visibility = View.VISIBLE
                    faIcon.visibility = View.GONE
                    Glide.with(context).load(uri).into(ivImages)
                }
                cvClick.setOnClickListener {
                    inputVideoClick.onClickAddVideo(cvClick, itemHaina, adapterPosition)
                }
                ivAction.setOnClickListener {
                    inputVideoClick.onClickAddVideo(ivAction, itemHaina, adapterPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInputVideos.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemInputImageBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterInputVideos.Holder, position: Int) {
        val image: VideoPostData = videoList!![position]
        holder.bind(image, inputVideoClick)
    }

    override fun getItemCount(): Int = videoList?.size!!

    interface InputVideoClick{
        fun onClickAddVideo(view: View, data:VideoPostData, position: Int)
    }

    fun add(imageString:MutableList<VideoPostData>){
        videoList?.addAll(imageString)
        notifyItemRangeInserted((videoList?.size?.minus(imageString.size)!!), imageString.size)
    }

    fun clear(){
        videoList?.clear()
        notifyDataSetChanged()
    }

}