package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemReviewHotelBinding
import haina.ecommerce.model.hotels.Reviews


class AdapterListReviews(val context: Context, private val listReviews: List<Reviews?>?):
    RecyclerView.Adapter<AdapterListReviews.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemReviewHotelBinding.bind(view)
        fun bind(itemHaina: Reviews){
            with(binding){
              tvReviewUser.text = itemHaina.description
                tvNameUser.text = itemHaina.nameUser
                ratingBar.rating = itemHaina.rating
                tvDateReviews.text = itemHaina.dateReviews
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListReviews.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemReviewHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListReviews.Holder, position: Int) {
        val photo: Reviews = listReviews?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listReviews?.size!!
}