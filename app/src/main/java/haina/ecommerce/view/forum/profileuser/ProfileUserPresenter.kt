package haina.ecommerce.view.forum.profileuser

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseFollowSubforum
import haina.ecommerce.model.forum.ResponseGetProfileUserForum
import haina.ecommerce.model.forum.ResponseUnfollowSubforum
import haina.ecommerce.view.forum.detailforum.DetailForumContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ProfileUserPresenter(val view:DetailForumContract.ViewProfileSubforum, val context: Context) {

    fun followSubforum(subforumId:Int){
        view.showLoading()
        val newComment = NetworkConfig().getConnectionHainaBearer(context).followSubforum(subforumId)
        newComment.enqueue(object : retrofit2.Callback<ResponseFollowSubforum>{
            override fun onResponse(call: Call<ResponseFollowSubforum>, response: Response<ResponseFollowSubforum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageFollowSubforum(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageFollowSubforum(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseFollowSubforum>, t: Throwable) {
                view.dismissLoading()
                view.messageFollowSubforum(t.localizedMessage.toString())
            }
        })
    }

    fun unfollowSubforum(subforumId:Int){
        view.showLoading()
        val newComment = NetworkConfig().getConnectionHainaBearer(context).unfollowSubforum(subforumId)
        newComment.enqueue(object : retrofit2.Callback<ResponseUnfollowSubforum>{
            override fun onResponse(call: Call<ResponseUnfollowSubforum>, response: Response<ResponseUnfollowSubforum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageFollowSubforum(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageFollowSubforum(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUnfollowSubforum>, t: Throwable) {
                view.dismissLoading()
                view.messageFollowSubforum(t.localizedMessage.toString())
            }

        })
    }

}