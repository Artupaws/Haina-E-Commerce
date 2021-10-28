package haina.ecommerce.view.restaurant.dashboard

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.ResponseShowProperty
import haina.ecommerce.model.restaurant.ResponseCuisineAndTypeList
import haina.ecommerce.model.restaurant.ResponseRestaurantList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RestaurantDashboardPresenter(val view: RestaurantDashboardContract.View, val context: Context) {

    fun getRestaurantList(cuisine:Int?,type:Int?,lat:Double,long:Double,page:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).showAllRestaurant(cuisine,type,lat,long,page)
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

    fun getRestaurantCuisineList(){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getRestaurantCuisineList()
        showProperty.enqueue(object : retrofit2.Callback<ResponseCuisineAndTypeList>{
            override fun onResponse(call: Call<ResponseCuisineAndTypeList>, response: Response<ResponseCuisineAndTypeList>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getRestaurantCuisine(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseCuisineAndTypeList>, t: Throwable) {
                view.dismissLoading()
                view.message(t.localizedMessage.toString())
            }
        })
    }

    fun getRestaurantTypeList(){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getRestaurantTypeList()
        showProperty.enqueue(object : retrofit2.Callback<ResponseCuisineAndTypeList>{
            override fun onResponse(call: Call<ResponseCuisineAndTypeList>, response: Response<ResponseCuisineAndTypeList>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getRestaurantType(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseCuisineAndTypeList>, t: Throwable) {
                view.dismissLoading()
                view.message(t.localizedMessage.toString())
            }
        })
    }



}