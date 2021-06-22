package haina.ecommerce.view.hotels.listcity

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
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

}