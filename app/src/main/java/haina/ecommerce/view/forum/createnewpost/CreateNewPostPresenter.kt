package haina.ecommerce.view.forum.createnewpost

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGetListSubforum
import haina.ecommerce.model.forum.ResponseGiveUpvote
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CreateNewPostPresenter(val view:CreateNewPostContract.View, val context: Context) {

    fun createNewPost(subforumId:Int, title:String, content:String, images:List<MultipartBody.Part>, videos:List<MultipartBody.Part>?){
        view.showLoading()
        val createNewPost = NetworkConfig().getConnectionHainaBearer(context).createNewPost(subforumId, title, content, images, videos)
        createNewPost.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageNewPost(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageNewPost(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageNewPost(t.localizedMessage.toString())
            }
        })
    }

    fun getListSubForum(){
        view.showLoading()
        val removeUpvote = NetworkConfig().getConnectionHainaBearer(context).getSubforum()
        removeUpvote.enqueue(object : retrofit2.Callback<ResponseGetListSubforum>{
            override fun onResponse(call: Call<ResponseGetListSubforum>, response: Response<ResponseGetListSubforum>) {
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

            override fun onFailure(call: Call<ResponseGetListSubforum>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListSubforum(t.localizedMessage.toString())
            }

        })
    }

}