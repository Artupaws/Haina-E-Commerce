package haina.ecommerce.view.restaurant.detail

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.restaurant.ResponseCuisineAndTypeList
import haina.ecommerce.model.restaurant.ResponseRestaurantDetail
import haina.ecommerce.model.restaurant.ResponseRestaurantList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RestaurantDetailPresenter(val view: RestaurantDetailContract.View, val context: Context) {

    fun getRestaurantDetail(restaurantId:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getRestaurantDetail(restaurantId)
        showProperty.enqueue(object : retrofit2.Callback<ResponseRestaurantDetail>{
            override fun onResponse(call: Call<ResponseRestaurantDetail>, response: Response<ResponseRestaurantDetail>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getRestaurantData(data)
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
    fun setRestaurantSaved(restaurantId:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).setRestaurantSaved(restaurantId)
        showProperty.enqueue(object : retrofit2.Callback<ResponseRestaurantDetail>{
            override fun onResponse(call: Call<ResponseRestaurantDetail>, response: Response<ResponseRestaurantDetail>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getRestaurantData(data)
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