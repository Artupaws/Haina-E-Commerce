package haina.ecommerce.view.hotels.dashboardhotel

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.ResponseGetListHotel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HotelPresenter(val view:HotelContract) {

    fun getAllHotel(){
        val getAllHotel = NetworkConfig().getNetworkHotel().getAllHotel()
        getAllHotel.enqueue(object : retrofit2.Callback<ResponseGetListHotel>{
            override fun onResponse(call: Call<ResponseGetListHotel>, response: Response<ResponseGetListHotel>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDataAllHotel(data)
                } else {
                    view.getMessageHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetListHotel>, t: Throwable) {
                view.getMessageHotel(t.localizedMessage.toString())
            }

        })
    }

}