package haina.ecommerce.view.hotels.dashboardhotel

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.ResponseGetHotelByCity
import haina.ecommerce.model.hotels.ResponseGetHotelByName
import haina.ecommerce.model.hotels.ResponseGetListCity
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

    fun getHotelByCity(cityId:Int){
        val getAllHotel = NetworkConfig().getNetworkHotel().getHotelByCity(cityId)
        getAllHotel.enqueue(object : retrofit2.Callback<ResponseGetHotelByCity>{
            override fun onResponse(call: Call<ResponseGetHotelByCity>, response: Response<ResponseGetHotelByCity>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDataAllHotel(data)
                } else {
                    view.getMessageHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetHotelByCity>, t: Throwable) {
                view.getMessageHotel(t.localizedMessage.toString())
            }

        })
    }

    fun getListCity(){
        val getAllCity = NetworkConfig().getNetworkHotel().getListCity()
        getAllCity.enqueue(object : retrofit2.Callback<ResponseGetListCity>{
            override fun onResponse(call: Call<ResponseGetListCity>, response: Response<ResponseGetListCity>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListCity(data)
                    view.getMessageHotel(response.body()?.message.toString())
                } else {
                    view.getMessageHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetListCity>, t: Throwable) {
                view.getMessageHotel(t.localizedMessage.toString())
            }

        })
    }

    fun getHotelByName(hotelName:String){
        val getHotelByName = NetworkConfig().getNetworkHotel().getHotelByName(hotelName)
        getHotelByName.enqueue(object : retrofit2.Callback<ResponseGetHotelByName>{
            override fun onResponse(call: Call<ResponseGetHotelByName>, response: Response<ResponseGetHotelByName>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDataAllHotel(data)
                    view.getMessageHotel(response.body()?.message.toString())
                } else {
                    view.getMessageHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetHotelByName>, t: Throwable) {
                view.getMessageHotel(t.localizedMessage.toString())
            }

        })
    }

}