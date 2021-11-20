package haina.ecommerce.view.restaurant.register

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.restaurant.response.ResponseCuisineAndTypeList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RegisterRestaurantPresenter (val view: RegisterRestaurantContract.View, val context: Context) {

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