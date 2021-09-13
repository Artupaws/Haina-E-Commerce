package haina.ecommerce.view.hotels.selection

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseHotelSearch
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.hotels.newHotel.ResponseGetHotelDarma
import haina.ecommerce.model.hotels.newHotel.ResponseGetListBooking
import haina.ecommerce.model.hotels.newHotel.ResponseGetRoomHotel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HotelSelectionPresenter(val view:HotelSelectionContract.View, val context: Context) {

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

    fun getSearchHotel(searchQuery:String,checkInDate:String,checkOutDate:String){
        view.showLoading()
        val getList = NetworkConfig().getConnectionToDarma(context).getHotelSearch(searchQuery,checkInDate,checkOutDate)
        getList.enqueue(object : retrofit2.Callback<ResponseHotelSearch>{
            override fun onResponse(call: Call<ResponseHotelSearch>, response: Response<ResponseHotelSearch>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.getSearch(response.body()?.data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ResponseHotelSearch>, t: Throwable) {
                view.dismissLoading()
            }
        })
    }
    fun getSearchHotelDone(searchQuery:String,checkInDate:String,checkOutDate:String){
        view.showLoading()
        val getList = NetworkConfig().getConnectionToDarma(context).getHotelSearch(searchQuery,checkInDate,checkOutDate)
        getList.enqueue(object : retrofit2.Callback<ResponseHotelSearch>{
            override fun onResponse(call: Call<ResponseHotelSearch>, response: Response<ResponseHotelSearch>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.search(response.body()?.data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ResponseHotelSearch>, t: Throwable) {
                view.dismissLoading()
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

    fun getRoomHotel(idHotel:String,idCity:String){
        view.showLoading()
        val getRoom = NetworkConfig().getConnectionToDarma(context).getDataRoomFromSearch(idHotel,idCity)
        getRoom.enqueue(object : retrofit2.Callback<ResponseGetRoomHotel>{
            override fun onResponse(call: Call<ResponseGetRoomHotel>, response: Response<ResponseGetRoomHotel>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataRoom
                    view.getDataRoom(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ResponseGetRoomHotel>, t: Throwable) {
                view.dismissLoading()
            }

        })
    }

}