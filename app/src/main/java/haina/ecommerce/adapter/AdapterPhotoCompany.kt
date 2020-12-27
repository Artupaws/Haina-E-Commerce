package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemPhotoCompanyBinding
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.PhotoItemDataCompany

class AdapterPhotoCompany(val context: Context, private val listPhoto: List<PhotoItemDataCompany?>?):
    RecyclerView.Adapter<AdapterPhotoCompany.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPhotoCompanyBinding.bind(view)
        fun bind(item: PhotoItemDataCompany){
            with(binding){
                Glide.with(context).load(item.photoUrl).skipMemoryCache(false).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivCompany)
                Log.d("photo", item.photoUrl)

                ivAction.setOnClickListener {
                    val deleteImage = Intent("delete")
                            .putExtra("idImage", item.id)
                    broadcaster?.sendBroadcast(deleteImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPhotoCompany.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoCompanyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPhotoCompany.Holder, position: Int) {
        val photo: PhotoItemDataCompany = listPhoto?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listPhoto?.size!!
}