package haina.ecommerce.view.forum.tab.myforum

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGetAllThreads
import haina.ecommerce.model.forum.ResponseGetHotpost
import haina.ecommerce.model.forum.ResponseGiveUpvote
import haina.ecommerce.view.forum.tab.showforum.ShowForumContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailMySubforumPresenter(val view:ShowForumContract.ViewDetailMySubforum, val context: Context) {

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


    fun getListForumPost(IdForum:Int,page:Int){
        view.showLoading()
        val getListForum = NetworkConfig().getConnectionHainaBearer(context).getListForumPost(IdForum,"time",page)
        getListForum.enqueue(object : retrofit2.Callback<ResponseGetAllThreads>{
            override fun onResponse(call: Call<ResponseGetAllThreads>, response: Response<ResponseGetAllThreads>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageListPost(response.body()?.message.toString())
                    val data = response.body()?.dataAllThreads
                    view.getListPost(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageListPost(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetAllThreads>, t: Throwable) {
                view.dismissLoading()
                view.messageListPost(t.localizedMessage.toString())
            }
        })
    }

}