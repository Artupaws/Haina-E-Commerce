package haina.ecommerce.view.restaurant.saved

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.restaurant.response.ResponseCuisineAndTypeList
import haina.ecommerce.model.restaurant.response.ResponseRestaurantList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SavedRestaurantPresenter (val view: SavedRestaurantContract.View, val context: Context){

    fun getSavedRestaurant(cuisine:Int?,type:Int?,halal:Int?,lat:Double,long:Double,page:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getSavedRestaurant(cuisine,type,halal,lat,long,page)
        showProperty.enqueue(object : retrofit2.Callback<ResponseRestaurantList>{
            override fun onResponse(call: Call<ResponseRestaurantList>, response: Response<ResponseRestaurantList>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getRestaurantList(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseRestaurantList>, t: Throwable) {
                view.dismissLoading()
                view.message(t.localizedMessage.toString())
            }
        })
    }

}