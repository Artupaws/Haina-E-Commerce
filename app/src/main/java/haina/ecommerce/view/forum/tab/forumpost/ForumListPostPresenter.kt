package haina.ecommerce.view.forum.tab.forumpost

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ForumListPostPresenter(val view: ForumListPostContract.View, val context: Context) {

    fun getCategory(){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).getCategoryForum()
        getCategory.enqueue(object : retrofit2.Callback<ResponseGetCategoryForum>{
            override fun onResponse(call: Call<ResponseGetCategoryForum>, response: Response<ResponseGetCategoryForum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetCategory(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListCategoryForum(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetCategory(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetCategoryForum>, t: Throwable) {
                view.dismissLoading()
                view.messageGetCategory(t.localizedMessage.toString())
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