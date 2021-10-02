package haina.ecommerce.view.forum.tab.profilecomment

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ProfileCommentListPresenter(val view: ProfileCommentListContract.View, val context: Context) {

    fun getListProfileComments(IdUser:Int,page:Int){
        view.showLoading()
        val getListForum = NetworkConfig().getConnectionHainaBearer(context).getUserComment(IdUser,"time",page)
        getListForum.enqueue(object : retrofit2.Callback<ResponseUserCommentList>{
            override fun onResponse(call: Call<ResponseUserCommentList>, response: Response<ResponseUserCommentList>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetComment(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListComment(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetComment(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUserCommentList>, t: Throwable) {
                view.dismissLoading()
                view.messageGetComment(t.localizedMessage.toString())
            }
        })
    }

}