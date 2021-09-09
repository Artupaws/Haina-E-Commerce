package haina.ecommerce.view.hotels.selection

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseHotelSearch
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.hotels.newHotel.ResponseGetHotelDarma
import haina.ecommerce.model.hotels.newHotel.ResponseGetListBooking
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class BottomSheetSearchHotelPresenter(val view:BottomSheetSearchHotelContract.View, val context: Context) {


    fun getSearchHotel(searchQuery:String,checkInDate:String,checkOutDate:String){
        view.showLoading()
        val getList = NetworkConfig().getConnectionHainaBearer(context).getHotelSearch(searchQuery,checkInDate,checkOutDate)
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


}