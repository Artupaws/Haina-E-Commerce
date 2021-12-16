package haina.ecommerce.adapter.restaurant.review

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.*
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.restaurant.master.ReviewData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants


class AdapterRestaurantReview(val context: Context,
                                    private val listReview: ArrayList<ReviewData?>?):
    RecyclerView.Adapter<AdapterRestaurantReview.Holder>() {

    private lateinit var sharedPref:SharedPreferenceHelper
    private var helper: Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRestaurantReviewBinding.bind(view)
        fun bind(data: ReviewData){
            with(binding){
                tvNamePoster.text = data.user!!.username
                tvReview.text = data.review
                tvRating.text = data.rating.toString()

                Glide.with(context).load(data.user.userPhoto).into(ivImagePoster)
                var datePost = helper.getTimeAgo(data.reviewDate)
                if(sharedPref.getValueString(Constants.LANGUAGE_APP) == "zh"){
                    datePost = datePost.replace("d", "天")
                    datePost = datePost.replace("h", "小时")
                }

                tvReviewDate.text = datePost

                if(!data.photos?.isNullOrEmpty()!!){
                    llImageReview.removeAllViewsInLayout()
                    val inflater = LayoutInflater.from(context)
                    llImageReview.visibility = View.VISIBLE

                    when(data.photos.size){
                        1 -> {
                            val imageview = LayoutOneImageForumBinding.inflate(inflater)
                            Glide.with(context).load(data.photos[0]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
//                            imageview.imageView1.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,0)
//                            }
                            llImageReview.addView(imageview.root)
                        }
                        2 -> {
                            val imageview = LayoutTwoImageForumBinding.inflate(inflater)
                            Glide.with(context).load(data.photos[0]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            Glide.with(context).load(data.photos[1]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
//                            imageview.imageView1.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,0)
//                            }
//                            imageview.imageView2.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,1)
//                            }
                            llImageReview.addView(imageview.root)
                        }

                        3 -> {
                            val imageview = LayoutThreeImageForumBinding.inflate(inflater)
                            Glide.with(context).load(data.photos[0]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            Glide.with(context).load(data.photos[1]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                            Glide.with(context).load(data.photos[2]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView3)
//                            imageview.imageView1.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,0)
//                            }
//                            imageview.imageView2.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,1)
//                            }
//                            imageview.imageView3.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,2)
//                            }
                            llImageReview.addView(imageview.root)
                        }

                        4 -> {
                            val imageview = LayoutFourImageForumBinding.inflate(inflater)
                            Glide.with(context).load(data.photos[0]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                            Glide.with(context).load(data.photos[1]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                            Glide.with(context).load(data.photos[2]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView3)
                            Glide.with(context).load(data.photos[3]!!.photoUrl).placeholder(R.drawable.skeleton_image).into(imageview.imageView4)
//                            imageview.imageView1.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,0)
//                            }
//                            imageview.imageView2.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,1)
//                            }
//                            imageview.imageView3.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,2)
//                            }
//                            imageview.imageView4.setOnClickListener {
//                                itemAdapterCallback.detailPhoto(data.photos,3)
//                            }
                            llImageReview.addView(imageview.root)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRestaurantReview.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRestaurantReviewBinding.inflate(inflater,parent,false)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterRestaurantReview.Holder, position: Int) {
        val photo: ReviewData = listReview?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listReview!!.size

    fun add(data:List<ReviewData?>?){
        listReview?.addAll(data!!)
        notifyItemRangeInserted((listReview?.size?.minus(data?.size!!)!!), data!!.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        listReview?.clear()
        notifyDataSetChanged()
    }
}