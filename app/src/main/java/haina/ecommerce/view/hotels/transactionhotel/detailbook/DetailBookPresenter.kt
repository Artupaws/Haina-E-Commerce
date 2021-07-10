package haina.ecommerce.view.hotels.transactionhotel.detailbook

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseCancelBookingHotel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailBookPresenter(val view:DetailBookContract.View, val context: Context) {

    fun cancelBookingHotel(bookingId:Int){
        view.showLoading()
        val cancelBookingHotel = NetworkConfig().getConnectionToDarma(context).cancelBookingHotelDarma(bookingId)
        cancelBookingHotel.enqueue(object : retrofit2.Callback<ResponseCancelBookingHotel>{
            override fun onResponse(call: Call<ResponseCancelBookingHotel>, response: Response<ResponseCancelBookingHotel>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCancelBookingHotel(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageCancelBookingHotel(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCancelBookingHotel>, t: Throwable) {
                view.dismissLoading()
                view.messageCancelBookingHotel(t.localizedMessage.toString())
            }

        })
    }
}