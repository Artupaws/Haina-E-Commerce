package haina.ecommerce.view.hotels.transactionhotel

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.transactionhotel.ResponseGetTransactionHotel
import retrofit2.Call
import retrofit2.Response

class HistoryTransactionHotelPresenter(val view: HistoryTransactionHotelContract, val context:Context) {

    fun getListTransactionHotel(){
        val getListTransaction = NetworkConfig().getNetworkHotelBearer(context).getListTransactionHotel()
        getListTransaction.enqueue(object : retrofit2.Callback<ResponseGetTransactionHotel>{
            override fun onResponse(call: Call<ResponseGetTransactionHotel>, response: Response<ResponseGetTransactionHotel>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataTransactionHotel
                    view.getListTransactionHotel(data)
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                } else {
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetTransactionHotel>, t: Throwable) {
                view.messageGetListTransactionHotel(t.localizedMessage.toString())
            }

        })
    }

}