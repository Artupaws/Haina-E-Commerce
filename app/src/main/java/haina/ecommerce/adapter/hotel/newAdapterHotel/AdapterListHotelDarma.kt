package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import haina.ecommerce.databinding.ListItemHotelsBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.DataHotel
import haina.ecommerce.model.hotels.newHotel.HotelsItem
import timber.log.Timber


class AdapterListHotelDarma(val context: Context, private val listHotel: List<HotelsItem?>?,
                            private val itemAdapterCallback:ItemAdapterCallBack):
    RecyclerView.Adapter<AdapterListHotelDarma.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemHotelsBinding.bind(view)
        fun bind(hotelHaina: HotelsItem, itemAdapterCallback:ItemAdapterCallBack){
            with(binding){
                tvNameHotel.text = hotelHaina.name
                tvLocationHotel.text = hotelHaina.address
                val startPrice = helper.convertToFormatMoneyIDRFilter(hotelHaina.priceStart.toString())
                tvStartPrice.text = startPrice
                cvClick.setOnClickListener { itemAdapterCallback.onClick(binding.cvClick, hotelHaina.iD!!) }
//                ivHotels.load("http://61.8.74.42:7080/H2H/hotel/logo?id=48659")
//                Picasso.get().load("http://61.8.74.42:7080/H2H/hotel/logo?id=48659").into(ivHotels)
                Glide.with(context)
                    .load("https://www.darmawisataindonesiah2h.co.id/hotel/Image?ID=2811467-1")
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,isFirstResource: Boolean): Boolean {
                            Timber.d(e!!)
                            return false
                        }
                        override fun onResourceReady(resource: Drawable?,model: Any?,target: Target<Drawable>?,dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            shimmerLoading.visibility=View.GONE
                            return false
                        }
                    })
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivHotels)


//                wvHotels.settings.useWideViewPort = true
//                wvHotels.settings.loadWithOverviewMode = true
//
//                wvHotels.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
//                wvHotels.loadUrl(HotelsItem.im "http://61.8.74.42:7080/H2H/hotel/logo?id=48659")


//                val frameVideo =
//                    "<html><body ><img style='width:100%' src='"+hotelHaina.logo+"'/></body></html>"
//                wvHotels.loadUrl(hotelHaina.logo!!)
//                wvHotels.loadData(frameVideo, "text/html", "utf-8")

                ratingBarHotel.rating = hotelHaina.rating!!
                val avgRating = "${hotelHaina.ratingAverage} - Avg Rating"
                tvAvgRating.text = avgRating
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHotelDarma.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHotelsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHotelDarma.Holder, position: Int) {
        val photo: HotelsItem = listHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listHotel?.size!!

    interface ItemAdapterCallBack{
        fun onClick(view: View, idHotel: String)
    }


}