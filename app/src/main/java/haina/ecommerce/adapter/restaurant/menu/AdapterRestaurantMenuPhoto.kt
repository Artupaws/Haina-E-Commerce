package haina.ecommerce.adapter.restaurant.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.restaurant.master.MenuImage
import haina.ecommerce.preference.SharedPreferenceHelper


class AdapterRestaurantMenuPhoto(val context: Context,
                                    private val listCategory: List<MenuImage?>?,val callback:ItemAdapterCallback):
    RecyclerView.Adapter<AdapterRestaurantMenuPhoto.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRestaurantMenuImageBinding.bind(view)

        fun bind(data: MenuImage, position: Int){
            with(binding){
                Glide.with(context).load(data.url).into(ivImage)
                ivImage.setOnClickListener {
                    callback.detailPhoto(listCategory,position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRestaurantMenuPhoto.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRestaurantMenuImageBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterRestaurantMenuPhoto.Holder, position: Int) {
        val photo: MenuImage = listCategory?.get(position)!!
        holder.bind(photo,position)
    }

    interface ItemAdapterCallback{
        fun detailPhoto(listImage: List<MenuImage?>?,position:Int)
    }

    override fun getItemCount(): Int = listCategory!!.size


}