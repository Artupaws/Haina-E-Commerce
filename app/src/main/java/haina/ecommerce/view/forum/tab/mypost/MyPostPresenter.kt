package haina.ecommerce.view.forum.tab.mypost

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class MyPostPresenter(val view: MyPostContract.View, val context: Context) {

    fun getListMypost(){
        view.showLoading()
        val removeUpvote = NetworkConfig().getConnectionHainaBearer(context).getMypost()
        removeUpvote.enqueue(object : retrofit2.Callback<ResponseGetMypost>{
            override fun onResponse(call: Call<ResponseGetMypost>, response: Response<ResponseGetMypost>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListMypost(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListMypost(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListMypost(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetMypost>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListMypost(t.localizedMessage.toString())
            }

        })
    }
    fun deleteMyPost(postId:Int){
        view.showLoading()
        val removeUpvote = NetworkConfig().getConnectionHainaBearer(context).deleteMyPost(postId)
        removeUpvote.enqueue(object : retrofit2.Callback<ResponseDeleteMyPost>{
            override fun onResponse(call: Call<ResponseDeleteMyPost>, response: Response<ResponseDeleteMyPost>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeleteMyPost(response.body()?.message.toString())
                    val id = response.body()?.data
                    view.getDataMyPostDeleted(id!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageDeleteMyPost(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseDeleteMyPost>, t: Throwable) {
                view.dismissLoading()
                view.messageDeleteMyPost(t.localizedMessage.toString())
            }

        })
    }
}