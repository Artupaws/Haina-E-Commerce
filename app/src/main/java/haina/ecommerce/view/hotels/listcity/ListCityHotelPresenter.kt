package haina.ecommerce.view.hotels.listcity

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.hotels.newHotel.ResponseGetHotelDarma
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListCityHotelPresenter(val view:ListCityHotelContract, val context: Context) {

    fun getListCity(){
        val getList = NetworkConfig().getConnectionHainaBearer(context).getListAllCityHotel()
        getList.enqueue(object : retrofit2.Callback<ResponseGetCityHotel>{
            override fun onResponse(call: Call<ResponseGetCityHotel>, response: Response<ResponseGetCityHotel>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListCity(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListCity(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListCity(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetCityHotel>, t: Throwable) {
                view.messageGetListCity(t.localizedMessage.toString())
            }
        })
    }

    fun getHotelDarma(countryId:String, cityId:Int, paxPassport:String, checkIn:String, checkOut:String){
        val getList = NetworkConfig().getConnectionHainaBearer(context).getHotelDarma(countryId, cityId, paxPassport, checkIn, checkOut)
        getList.enqueue(object : retrofit2.Callback<ResponseGetHotelDarma>{
            override fun onResponse(call: Call<ResponseGetHotelDarma>, response: Response<ResponseGetHotelDarma>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetHotelDarma(response.body()?.message.toString())
                    val data = response.body()?.dataHotelDarma
                    view.getHotelDarma(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetHotelDarma(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetHotelDarma>, t: Throwable) {
                view.messageGetHotelDarma(t.localizedMessage.toString())
            }

        })
    }

}