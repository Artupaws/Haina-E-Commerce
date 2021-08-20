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
import haina.ecommerce.model.forum.ImagePostData

class AdapterInputImages(private val context: Context, private val imageList: MutableList<ImagePostData>?,
                         val itemAdapterCallback: ItemAdapterCallback): RecyclerView.Adapter<AdapterInputImages.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemInputImageBinding.bind(itemView)
        fun bind(itemHaina: ImagePostData, itemAdapterCallback: ItemAdapterCallback){
            with(binding) {
                if (itemHaina.id == -1){
                    ivAction.visibility = View.GONE
                } else {
                    ivAction.visibility = View.VISIBLE
                }
                if (itemHaina.image.contains("&#xf067;")) {
                    val icon = HtmlCompat.fromHtml(itemHaina.image, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    faIcon.text = icon
                    ivImages.visibility = View.GONE
                    faIcon.visibility = View.VISIBLE
                }else {
                    val uri = Uri.parse(itemHaina.image)
                    ivImages.visibility = View.VISIBLE
                    faIcon.visibility = View.GONE
                    Glide.with(context).load(uri).into(ivImages)
                }
                cvClick.setOnClickListener {
                    itemAdapterCallback.onClickAddImage(cvClick, itemHaina, adapterPosition)
                }
                ivAction.setOnClickListener {
                    itemAdapterCallback.onClickAddImage(ivAction, itemHaina, adapterPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInputImages.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemInputImageBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterInputImages.Holder, position: Int) {
        val image: ImagePostData = imageList!![position]
        holder.bind(image, itemAdapterCallback)
    }

    override fun getItemCount(): Int = imageList?.size!!

    interface ItemAdapterCallback{
        fun onClickAddImage(view: View, data:ImagePostData, position: Int)
    }

    fun add(imageString:MutableList<ImagePostData>){
        imageList?.addAll(imageString)
        notifyItemRangeInserted((imageList?.size?.minus(imageString.size)!!), imageString.size)
    }

    fun clear(){
        imageList?.clear()
        notifyDataSetChanged()
    }

}