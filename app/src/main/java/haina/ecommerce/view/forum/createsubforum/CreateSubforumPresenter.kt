package haina.ecommerce.view.forum.createsubforum

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGiveUpvote
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CreateSubforumPresenter(val view:CreateSubforumContract.View, val context: Context) {

    fun createSubforum(name:String, description:String, categoryId:Int, image:MultipartBody.Part){
        view.showLoading()
        val createSubforum = NetworkConfig().getConnectionHainaBearer(context).createSubForum(name, description, categoryId, image)
        createSubforum.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateSubforum(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageCreateSubforum(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageCreateSubforum(t.localizedMessage.toString())
            }

        })
    }

}