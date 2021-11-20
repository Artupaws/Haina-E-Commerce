package haina.ecommerce.view.restaurant.detail.review.addreview

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGiveUpvote
import haina.ecommerce.model.restaurant.response.ResponseRestaurantAddReview
import haina.ecommerce.model.restaurant.response.ResponseRestaurantReviewList
import haina.ecommerce.view.restaurant.detail.review.RestaurantReviewListContract
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RestaurantAddReviewPresenter(val view: RestaurantAddReviewContract.View, val context: Context)  {

    fun createReview(restaurantId:Int, rating:Int, content: RequestBody, images:List<MultipartBody.Part>){
        view.showLoading()
        val createNewPost = NetworkConfig().getConnectionHainaBearer(context).createRestaurantReview(restaurantId, rating, content, images)
        createNewPost.enqueue(object : retrofit2.Callback<ResponseRestaurantAddReview>{
            override fun onResponse(call: Call<ResponseRestaurantAddReview>, response: Response<ResponseRestaurantAddReview>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.code(),response.body()?.message.toString())
                    view.addReview(response.body()!!.data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(response.code(),error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseRestaurantAddReview>, t: Throwable) {
                view.dismissLoading()
                view.message(t.hashCode(),t.localizedMessage.toString())
            }
        })
    }
}