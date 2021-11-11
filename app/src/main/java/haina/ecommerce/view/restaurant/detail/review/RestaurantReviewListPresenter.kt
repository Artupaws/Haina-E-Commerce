package haina.ecommerce.view.restaurant.detail.review

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.restaurant.response.ResponseRestaurantReviewList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RestaurantReviewListPresenter(val view: RestaurantReviewListContract, val context: Context)  {

    fun getReviewList(restaurantId:Int,page:Int){
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getRestaurantReview(restaurantId,page)
        showProperty.enqueue(object : retrofit2.Callback<ResponseRestaurantReviewList>{
            override fun onResponse(call: Call<ResponseRestaurantReviewList>, response: Response<ResponseRestaurantReviewList>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.code(),response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getReviewList(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(response.code(),error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseRestaurantReviewList>, t: Throwable) {
                view.message(t.hashCode(),t.localizedMessage.toString())
            }
        })
    }
}