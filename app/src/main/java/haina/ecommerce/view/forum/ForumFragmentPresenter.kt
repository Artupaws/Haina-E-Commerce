package haina.ecommerce.view.forum

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseSearchForum
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ForumFragmentPresenter(val view:ForumFragmentContract.View, val context:Context) {

    fun getSearch(keyword:String){
        view.showLoading()
        val getSearch = NetworkConfig().getConnectionHainaBearer(context).searchForum(keyword)
        getSearch.enqueue(object : retrofit2.Callback<ResponseSearchForum>{
            override fun onResponse(call: Call<ResponseSearchForum>, response: Response<ResponseSearchForum>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetSearch(response.body()?.message.toString())
                    val data = response.body()?.dataSearch
                    view.getDataSearch(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetSearch(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseSearchForum>, t: Throwable) {
                view.dismissLoading()
                view.messageGetSearch(t.localizedMessage.toString())
            }

        })
    }

}