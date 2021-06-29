package haina.ecommerce.view.hotels.listroom

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetPricePolicy
import haina.ecommerce.model.hotels.newHotel.SpecialRequestArrayItem
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListRoomPresenter(val view:ListRoomContract.View, val context: Context) {

    fun getPricePolicy(room_id:String, breakfastStatus:String) {
        view.showLoading()
        val getPricePolicy = NetworkConfig().getConnectionHainaBearer(context)
            .getPricePolicy(room_id, breakfastStatus)
        getPricePolicy.enqueue(object : retrofit2.Callback<ResponseGetPricePolicy> {
            override fun onResponse(call: Call<ResponseGetPricePolicy>, response: Response<ResponseGetPricePolicy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1) {
                    view.messageGetPricePolicy(response.body()?.message.toString())
                    val data = response.body()?.dataPricePolicy
                    view.getPricePolicy(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetPricePolicy(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetPricePolicy>, t: Throwable) {
                view.dismissLoading()
                view.messageGetPricePolicy(t.localizedMessage.toString())
            }

        })
    }

}