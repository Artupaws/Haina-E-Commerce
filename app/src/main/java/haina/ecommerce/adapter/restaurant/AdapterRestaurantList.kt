package haina.ecommerce.adapter.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.forum.ImagesItem
import haina.ecommerce.model.forum.ThreadsItem
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import timber.log.Timber


class AdapterRestaurantList(val context: Context,
                            private val listRestaurant: java.util.ArrayList<RestaurantData?>?,
                            val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterRestaurantList.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var imagesListener : ImageListener
    private lateinit var listParams: ArrayList<String>
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRestaurantBinding.bind(view)
        fun bind(data: RestaurantData, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                binding.tvRestaurantName.text = data.name

                var distance:String = data.distance!!.toString()+"Km"
                if(data.distance!!<1){
                    distance = (data.distance!!*100).toString()+"m"
                }
                binding.tvDistance.text = distance
                if(data.rating?.toFloat()!! >=5){
                    cvRating.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rating_5))
                }else if(data.rating?.toFloat()!! >=4){
                    cvRating.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rating_4))
                }else if(data.rating?.toFloat()!! >=3){
                    cvRating.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rating_3))
                }else if(data.rating?.toFloat()!! >=2){
                    cvRating.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rating_2))
                }else if(data.rating?.toFloat()!! >=1){
                    cvRating.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rating_1))
                }else{
                    cvRating.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rating_0))
                }

                binding.tvRating.text = data.rating

                binding.tvRestaurantAddress.text = data.address

                var cuisineString:String = ""
                data.cuisine!!.forEach {
                    cuisineString += it!!.name
                    if(it != data.cuisine.last()){
                        cuisineString +=", "
                    }
                }
                var typeString:String = ", "
                data.type!!.forEach {
                    typeString += it!!.name

                    if(it != data.type.last()){
                        typeString +=", "
                    }
                }
                binding.tvRestaurantType.text = "$cuisineString$typeString"
                Glide.with(context).load(data.photo!![0]!!.url).placeholder(R.drawable.skeleton_image).into(binding.ivRestaurant)

                binding.cvRestaurant.setOnClickListener {
                    itemAdapterCallback.restaurantDataOnClick(data)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRestaurantList.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRestaurantBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterRestaurantList.Holder, position: Int) {
        val photo: RestaurantData = listRestaurant?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listRestaurant?.size!!

    interface ItemAdapterCallback{
        fun restaurantDataOnClick(data:RestaurantData)
    }

    fun add(data:List<RestaurantData?>?){
        listRestaurant?.addAll(data!!)
        notifyItemRangeInserted((listRestaurant?.size?.minus(data?.size!!)!!), data!!.size)
    }

    fun clear(){
        listRestaurant?.clear()
        notifyDataSetChanged()
    }

}