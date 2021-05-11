package haina.ecommerce.view.hotels.transactionhotel.finish

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.ResponseInputRating
import retrofit2.Call
import retrofit2.Response

class FinishHotelpresenter(val view:FinishHotelContract, val context: Context) {

    fun inputRatingHotel(idHotel:Int, rating:Float, review:String){
        val inputRating = NetworkConfig().getConnectionHainaBearer(context).inputRating(idHotel, rating, review)
        inputRating.enqueue(object : retrofit2.Callback<ResponseInputRating>{
            override fun onResponse(call: Call<ResponseInputRating>, response: Response<ResponseInputRating>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageInputReview(response.body()?.message.toString())
                } else {
                    view.messageInputReview(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseInputRating>, t: Throwable) {
                view.messageInputReview(t.localizedMessage.toString())
            }

        })

    }

}