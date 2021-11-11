package haina.ecommerce.adapter.restaurant.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.AdapterRestaurantFoodCategory
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.ImagesItem
import haina.ecommerce.model.forum.ThreadsItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.MenuCategory
import haina.ecommerce.model.restaurant.master.MenuImage
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterRestaurantMenuPhoto(val context: Context,
                                    private val listCategory: List<MenuImage?>?):
    RecyclerView.Adapter<AdapterRestaurantMenuPhoto.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper

    private var count:Int = 0
    init {
        if(listCategory!!.size<3){
            count=listCategory.size
        }else{
            count=3

        }
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRestaurantMenuImageBinding.bind(view)

        fun bind(data: MenuImage){
            with(binding){
                Glide.with(context).load(data.url).skipMemoryCache(true).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImage)
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
        holder.bind(photo)
    }

    override fun getItemCount(): Int = count


}