package haina.ecommerce.view.forum.tab.myforum

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class MySubforumPresenter(val view: MySubforumContract.View, val context: Context) {

    fun getListSubForum(){
        view.showLoading()
        val removeUpvote = NetworkConfig().getConnectionHainaBearer(context).getMySubforum()
        removeUpvote.enqueue(object : retrofit2.Callback<ResponseGetMySubforum>{
            override fun onResponse(call: Call<ResponseGetMySubforum>, response: Response<ResponseGetMySubforum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListSubforum(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListSubforum(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListSubforum(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetMySubforum>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListSubforum(t.localizedMessage.toString())
            }
        })
    }

    fun getListSubForumFollow(){
        view.showLoading()
        val removeUpvote = NetworkConfig().getConnectionHainaBearer(context).getFollowSubforum()
        removeUpvote.enqueue(object : retrofit2.Callback<ResponseGetListSubforum>{
            override fun onResponse(call: Call<ResponseGetListSubforum>, response: Response<ResponseGetListSubforum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListFollow(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListSubforumFollow(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListFollow(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListSubforum>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListFollow(t.localizedMessage.toString())
            }

        })
    }

}