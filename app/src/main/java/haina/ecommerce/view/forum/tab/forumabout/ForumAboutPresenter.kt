package haina.ecommerce.view.forum.tab.forumabout

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGetCategoryForum
import haina.ecommerce.model.forum.ResponseModList
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ForumAboutPresenter (val view: ForumAboutContract.View, val context: Context) {

    fun getAbout(){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).getCategoryForum()
        getCategory.enqueue(object : retrofit2.Callback<ResponseGetCategoryForum>{
            override fun onResponse(call: Call<ResponseGetCategoryForum>, response: Response<ResponseGetCategoryForum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAbout(response.body()?.message.toString())
//                    val data = response.body()?.data
//                    view.getListCategoryForum(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAbout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetCategoryForum>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAbout(t.localizedMessage.toString())
            }
        })
    }
    fun getModList(idForum:Int){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).getModList(idForum)
        getCategory.enqueue(object : retrofit2.Callback<ResponseModList>{
            override fun onResponse(call: Call<ResponseModList>, response: Response<ResponseModList>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAbout(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListModerator(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAbout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseModList>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAbout(t.localizedMessage.toString())
            }
        })
    }
}