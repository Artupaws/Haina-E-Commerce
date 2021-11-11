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
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.AdapterRestaurantFoodCategory
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.ImagesItem
import haina.ecommerce.model.forum.ThreadsItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.MenuCategory
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterRestaurantMenuCategory(val context: Context,
                                    private val listCategory: List<MenuCategory?>?):
    RecyclerView.Adapter<AdapterRestaurantMenuCategory.Holder>() {

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
        private val binding = ListItemRestaurantMenuCategoryBinding.bind(view)

        private val adapterPhoto by lazy {
        }

        fun bind(data: MenuCategory){
            with(binding){
                tvCategoryName.text = data.menuName
                rvMenuImage.adapter= AdapterRestaurantMenuPhoto(context, data.menuImages)
                rvMenuImage.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRestaurantMenuCategory.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRestaurantMenuCategoryBinding.inflate(inflater,parent,false)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterRestaurantMenuCategory.Holder, position: Int) {
        val photo: MenuCategory = listCategory?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = count

}