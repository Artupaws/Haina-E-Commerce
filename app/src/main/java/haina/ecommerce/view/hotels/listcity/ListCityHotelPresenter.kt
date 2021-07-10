package haina.ecommerce.view.hotels.listcity

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.hotels.newHotel.ResponseGetHotelDarma
import haina.ecommerce.model.hotels.newHotel.ResponseGetListBooking
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListCityHotelPresenter(val view:ListCityHotelContract.View, val context: Context) {

    fun getListCity(){
        view.showLoading()
        val getList = NetworkConfig().getConnectionHainaBearer(context).getListAllCityHotel()
        getList.enqueue(object : retrofit2.Callback<ResponseGetCityHotel>{
            override fun onResponse(call: Call<ResponseGetCityHotel>, response: Response<ResponseGetCityHotel>) {
                view.dismissLoading()
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
                view.dismissLoading()
                view.messageGetListCity(t.localizedMessage.toString())
            }
        })
    }

    fun getHotelDarma(countryId:String, cityId:Int, paxPassport:String, checkIn:String, checkOut:String){
        view.showLoading()
        val getList = NetworkConfig().getConnectionToDarma(context).getHotelDarma(countryId, cityId, paxPassport, checkIn, checkOut)
        getList.enqueue(object : retrofit2.Callback<ResponseGetHotelDarma>{
            override fun onResponse(call: Call<ResponseGetHotelDarma>, response: Response<ResponseGetHotelDarma>) {
                view.dismissLoading()
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
                view.dismissLoading()
                view.messageGetHotelDarma(t.localizedMessage.toString())
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
                    val data = response.body()?.dataBooking?.unpaid?.size
                    view.getSizeListUnfinish(data)
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

}