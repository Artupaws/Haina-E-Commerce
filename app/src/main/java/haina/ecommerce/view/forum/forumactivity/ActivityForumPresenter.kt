package haina.ecommerce.view.forum.forumactivity

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGetListBan
import haina.ecommerce.model.forum.ResponseGetListRoleMod
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ActivityForumPresenter(val view:ActivityForumContract.View, val context:Context) {

    fun getListRoleMod(){
        view.showLoading()
        val getListRoleMod = NetworkConfig().getConnectionHainaBearer(context).getListRoleSubforum()
        getListRoleMod.enqueue(object : retrofit2.Callback<ResponseGetListRoleMod>{
            override fun onResponse(call: Call<ResponseGetListRoleMod>, response: Response<ResponseGetListRoleMod>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListMod(response.body()?.message.toString())
                    val data = response.body()?.data?.modList
                    view.getDataListMod(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListMod(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListRoleMod>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListMod(t.localizedMessage.toString())
            }

        })
    }

    fun getListBan(){
        view.showLoading()
        val getListRoleMod = NetworkConfig().getConnectionHainaBearer(context).getListBan()
        getListRoleMod.enqueue(object : retrofit2.Callback<ResponseGetListBan>{
            override fun onResponse(call: Call<ResponseGetListBan>, response: Response<ResponseGetListBan>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListBan(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataListBan(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListBan(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListBan>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListBan(t.localizedMessage.toString())
            }

        })
    }

}