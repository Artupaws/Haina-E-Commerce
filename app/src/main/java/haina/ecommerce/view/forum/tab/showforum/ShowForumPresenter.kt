package haina.ecommerce.view.forum.tab.showforum

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ShowForumPresenter(val view: ShowForumContract.View, val context: Context) {

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

    fun getListHotThreads(){
        view.showLoading()
        val getListForum = NetworkConfig().getConnectionHainaBearer(context).getListHotThreads()
        getListForum.enqueue(object : retrofit2.Callback<ResponseGetHotpost>{
            override fun onResponse(call: Call<ResponseGetHotpost>, response: Response<ResponseGetHotpost>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListForum(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListForum(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListForum(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetHotpost>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListForum(t.localizedMessage.toString())
            }
        })
    }

    fun getListAllThreads(page:Int){
        view.showLoading()
        val getListForum = NetworkConfig().getConnectionHainaBearer(context).getAllThreads(page)
        getListForum.enqueue(object : retrofit2.Callback<ResponseGetAllThreads>{
            override fun onResponse(call: Call<ResponseGetAllThreads>, response: Response<ResponseGetAllThreads>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAllThreads(response.body()?.message.toString())
                    val data = response.body()?.dataAllThreads
                    view.getListAllThreads(data?.threads)
                    response.body()?.dataAllThreads?.totalPage?.let { view.getTotalPage(it) }
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAllThreads(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetAllThreads>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAllThreads(t.localizedMessage.toString())
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