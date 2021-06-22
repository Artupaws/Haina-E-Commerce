package haina.ecommerce.view.hotels.listhotel

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetRoomHotel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListHotelPresenter(val view:ListHotelContract, val context: Context) {

    fun getRoomHotel(idHotel:String){
        val getRoom = NetworkConfig().getConnectionHainaBearer(context).getDataRoom(idHotel)
        getRoom.enqueue(object : retrofit2.Callback<ResponseGetRoomHotel>{
            override fun onResponse(call: Call<ResponseGetRoomHotel>, response: Response<ResponseGetRoomHotel>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetRoomHotel(response.body()?.message.toString())
                    val data = response.body()?.dataRoom
                    view.getDataRoom(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetRoomHotel(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetRoomHotel>, t: Throwable) {
                view.messageGetRoomHotel(t.localizedMessage.toString())
            }

        })
    }

}