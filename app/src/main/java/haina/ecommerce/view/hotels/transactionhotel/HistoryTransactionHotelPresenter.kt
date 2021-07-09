package haina.ecommerce.view.hotels.transactionhotel

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseCancelBookingHotel
import haina.ecommerce.model.hotels.newHotel.ResponseGetListBooking
import haina.ecommerce.model.hotels.transactionhotel.ResponseGetTransactionHotel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HistoryTransactionHotelPresenter(val view: HistoryTransactionHotelContract.View, val context:Context) {

    fun getListTransactionHotel(){
        view.showLoading()
        val getListTransaction = NetworkConfig().getConnectionToDarma(context).getListTransactionHotel()
        getListTransaction.enqueue(object : retrofit2.Callback<ResponseGetTransactionHotel>{
            override fun onResponse(call: Call<ResponseGetTransactionHotel>, response: Response<ResponseGetTransactionHotel>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataTransactionHotel
                    view.getListTransactionHotel(data)
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                } else {
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetTransactionHotel>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListTransactionHotel(t.localizedMessage.toString())
            }

        })
    }

    fun getListTransactionHotelDarma(){
        view.showLoading()
        val getListTransaction = NetworkConfig().getConnectionToDarma(context).getListBookingHotelDarma()
        getListTransaction.enqueue(object : retrofit2.Callback<ResponseGetListBooking>{
            override fun onResponse(call: Call<ResponseGetListBooking>, response: Response<ResponseGetListBooking>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataBooking
                    view.getListBookingHotelDarma(data)
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                } else {
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetListBooking>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListTransactionHotel(t.localizedMessage.toString())
            }

        })
    }

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