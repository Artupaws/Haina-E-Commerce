package haina.ecommerce.adapter.restaurant.photogallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.restaurant.master.RestaurantPhoto
import haina.ecommerce.preference.SharedPreferenceHelper


class AdapterRestaurantPhotoGallery(val context: Context,
                            private val listRestaurant: List<RestaurantPhoto?>?,
                            val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterRestaurantPhotoGallery.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRestaurantPhotoBinding.bind(view)
        fun bind(data: RestaurantPhoto, itemAdapterCallback: ItemAdapterCallback,position: Int){
            with(binding){
                Glide.with(context).load(data.url).placeholder(R.drawable.skeleton_image).into(ivImage)
                ivImage.setOnClickListener { itemAdapterCallback.photoDetail(listRestaurant,position) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRestaurantPhotoGallery.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRestaurantPhotoBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterRestaurantPhotoGallery.Holder, position: Int) {
        val photo: RestaurantPhoto = listRestaurant?.get(position)!!
        holder.bind(photo, itemAdapterCallback,position)
    }

    override fun getItemCount(): Int = listRestaurant?.size!!

    interface ItemAdapterCallback{
        fun photoDetail(listPhoto:List<RestaurantPhoto?>?,position:Int)
    }

}