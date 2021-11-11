package haina.ecommerce.adapter.restaurant

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
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterRestaurantFoodCategory(val context: Context,
                            private val listCategory: java.util.ArrayList<CuisineAndTypeData?>?):
    RecyclerView.Adapter<AdapterRestaurantFoodCategory.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemTagsRestaurantBinding.bind(view)
        fun bind(data: CuisineAndTypeData){
            with(binding){
                binding.tvTags.text = data.name

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRestaurantFoodCategory.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTagsRestaurantBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterRestaurantFoodCategory.Holder, position: Int) {
        val photo: CuisineAndTypeData = listCategory?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listCategory?.size!!

    fun add(data:List<CuisineAndTypeData?>?){
        listCategory?.addAll(data!!)
        notifyItemRangeInserted((listCategory?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listCategory?.clear()
        notifyDataSetChanged()
    }

}