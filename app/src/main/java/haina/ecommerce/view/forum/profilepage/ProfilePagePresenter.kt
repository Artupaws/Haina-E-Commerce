package haina.ecommerce.view.forum.profilepage

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import haina.ecommerce.view.forum.tab.showforum.ShowForumContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ProfilePagePresenter(val view:ProfilePageContract, val context: Context) {

    fun giveUpvote(postId:Int){
        val giveUpvote = NetworkConfig().getConnectionHainaBearer(context).upvote(postId)
        giveUpvote.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGiveUpvote(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGiveUpvote(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.messageGiveUpvote(t.localizedMessage.toString())
            }

        })
    }

    fun removeUpvote(postId:Int){
        val removeUpvote = NetworkConfig().getConnectionHainaBearer(context).removeUpvote(postId)
        removeUpvote.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGiveUpvote(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGiveUpvote(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.messageGiveUpvote(t.localizedMessage.toString())
            }

        })
    }


    fun getProfileData(IdUser:Int){
        view.showLoading()
        val getListForum = NetworkConfig().getConnectionHainaBearer(context).getUserProfileForum(IdUser)
        getListForum.enqueue(object : retrofit2.Callback<ResponseGetProfileUserForum>{
            override fun onResponse(call: Call<ResponseGetProfileUserForum>, response: Response<ResponseGetProfileUserForum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageListPost(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getProfileData(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageListPost(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetProfileUserForum>, t: Throwable) {
                view.dismissLoading()
                view.messageListPost(t.localizedMessage.toString())
            }
        })
    }




}