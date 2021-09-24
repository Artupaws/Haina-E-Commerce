package haina.ecommerce.view.forum.detailforum

import android.content.Context
import haina.ecommerce.adapter.forum.ResponseGetPostDetail
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailForumPresenter(val view:DetailForumContract.View, val context: Context) {


    fun getPostDetail(postId:Int){
        view.showLoading()
        val getListComment = NetworkConfig().getConnectionHainaBearer(context).getPostDetail(postId)
        getListComment.enqueue(object : retrofit2.Callback<ResponseGetPostDetail>{
            override fun onResponse(call: Call<ResponseGetPostDetail>, response: Response<ResponseGetPostDetail>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetComment(response.body()?.message.toString())
                    val data = response.body()?.data!!
                    view.getPostDetail(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetComment(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetPostDetail>, t: Throwable) {
                view.dismissLoading()
                view.messageGetComment(t.localizedMessage.toString())
            }

        })
    }

    fun getListComment(postId:Int){
        view.showLoading()
        val getListComment = NetworkConfig().getConnectionHainaBearer(context).getListComment(postId)
        getListComment.enqueue(object : retrofit2.Callback<ResponseGetListComment>{
            override fun onResponse(call: Call<ResponseGetListComment>, response: Response<ResponseGetListComment>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetComment(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListComment(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetComment(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListComment>, t: Throwable) {
                view.dismissLoading()
                view.messageGetComment(t.localizedMessage.toString())
            }

        })
    }

    fun newComment(postId:Int, comment:String){
        view.showLoading()
        val newComment = NetworkConfig().getConnectionHainaBearer(context).newComment(postId,comment)
        newComment.enqueue(object : retrofit2.Callback<ResponseNewComment>{
            override fun onResponse(call: Call<ResponseNewComment>, response: Response<ResponseNewComment>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageNewComment(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageNewComment(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseNewComment>, t: Throwable) {
                view.dismissLoading()
                view.messageNewComment(t.localizedMessage.toString())
            }

        })
    }

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

    fun assignSubMod(subforumID:Int, userId:Int, role:String){
        view.showLoading()
        val assignSubMod = NetworkConfig().getConnectionHainaBearer(context).assignMod(subforumID,userId, role)
        assignSubMod.enqueue(object : retrofit2.Callback<ResponseAddMod>{
            override fun onResponse(call: Call<ResponseAddMod>, response: Response<ResponseAddMod>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAssignSubMod(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageAssignSubMod(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddMod>, t: Throwable) {
                view.dismissLoading()
                view.messageAssignSubMod(t.localizedMessage.toString())
            }

        })
    }

    fun deleteComment(commentID:Int){
        view.showLoading()
        val deleteComment = NetworkConfig().getConnectionHainaBearer(context).deleteComment(commentID)
        deleteComment.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeleteComment(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageDeleteComment(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageDeleteComment(t.localizedMessage.toString())
            }

        })
    }

}