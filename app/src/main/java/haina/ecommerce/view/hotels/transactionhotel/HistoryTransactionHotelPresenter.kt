package haina.ecommerce.view.hotels.transactionhotel

import android.content.Context
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.ResponseGetListTransactionHotel
import retrofit2.Call
import retrofit2.Response

class HistoryTransactionHotelPresenter(val view: HistoryTransactionHotelContract, val context:Context) {

    fun getListTransactionHotel(){
        val getListTransaction = NetworkConfig().getNetworkHotelBearer(context).getListTransactionHotel()
        getListTransaction.enqueue(object : retrofit2.Callback<ResponseGetListTransactionHotel>{
            override fun onResponse(call: Call<ResponseGetListTransactionHotel>, response: Response<ResponseGetListTransactionHotel>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListTransactionHotel(data)
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                } else {
                    view.messageGetListTransactionHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetListTransactionHotel>, t: Throwable) {
                view.messageGetListTransactionHotel(t.localizedMessage.toString())
            }

        })
    }

}