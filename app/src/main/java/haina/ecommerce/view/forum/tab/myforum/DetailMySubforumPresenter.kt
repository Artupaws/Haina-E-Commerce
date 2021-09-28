package haina.ecommerce.view.forum.tab.myforum

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGetAllThreads
import haina.ecommerce.model.forum.ResponseGetHotpost
import haina.ecommerce.model.forum.ResponseGiveUpvote
import haina.ecommerce.model.forum.ResponseSubforumData
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


    fun getSubforumData(IdForum:Int){
        view.showLoading()
        val getListForum = NetworkConfig().getConnectionHainaBearer(context).getSubforumData(IdForum)
        getListForum.enqueue(object : retrofit2.Callback<ResponseSubforumData>{
            override fun onResponse(call: Call<ResponseSubforumData>, response: Response<ResponseSubforumData>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageListPost(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getSubforumData(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageListPost(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseSubforumData>, t: Throwable) {
                view.dismissLoading()
                view.messageListPost(t.localizedMessage.toString())
            }
        })
    }

}