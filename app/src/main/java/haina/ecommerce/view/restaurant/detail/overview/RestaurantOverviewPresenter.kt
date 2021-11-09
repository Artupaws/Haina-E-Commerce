package haina.ecommerce.view.restaurant.detail.overview

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.restaurant.ResponseRestaurantDetail
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RestaurantOverviewPresenter(val view: RestaurantOverviewContract.View, val context: Context)  {

    fun getRestaurantMenu(restaurantId:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getRestaurantMenu(restaurantId)
        showProperty.enqueue(object : retrofit2.Callback<ResponseRestaurantDetail>{
            override fun onResponse(call: Call<ResponseRestaurantDetail>, response: Response<ResponseRestaurantDetail>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getMenuList(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseRestaurantDetail>, t: Throwable) {
                view.dismissLoading()
                view.message(t.localizedMessage.toString())
            }
        })
    }
}