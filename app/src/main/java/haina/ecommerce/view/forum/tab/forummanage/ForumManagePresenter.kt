package haina.ecommerce.view.forum.tab.forummanage

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ForumManagePresenter (val view: ForumManageContract.View, val context: Context) {

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

    fun removeModerator(idForum:Int,idUser:Int){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).removeMod(idForum,idUser)
        getCategory.enqueue(object : retrofit2.Callback<ResponseRemoveModerator>{
            override fun onResponse(call: Call<ResponseRemoveModerator>, response: Response<ResponseRemoveModerator>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAbout(response.body()?.message.toString())
                    val data = response.body()?.data!!
                    view.getRemoveData(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAbout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseRemoveModerator>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAbout(t.localizedMessage.toString())
            }
        })
    }

    fun addModerator(idForum:Int,idUser:Int,role:String){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).addMod(idForum,idUser,role)
        getCategory.enqueue(object : retrofit2.Callback<ResponseRemoveModerator>{
            override fun onResponse(call: Call<ResponseRemoveModerator>, response: Response<ResponseRemoveModerator>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAbout(response.body()?.message.toString())
                    val data = response.body()?.data!!
                    view.getRemoveData(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAbout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseRemoveModerator>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAbout(t.localizedMessage.toString())
            }
        })
    }

    fun getBanList(idForum:Int){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).getForumBannedList(idForum)
        getCategory.enqueue(object : retrofit2.Callback<ResponseForumBannedList>{
            override fun onResponse(call: Call<ResponseForumBannedList>, response: Response<ResponseForumBannedList>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAbout(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListBannedUser(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAbout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseForumBannedList>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAbout(t.localizedMessage.toString())
            }
        })
    }
    fun removeBanned(idForum:Int,idUser:Int){
        view.showLoading()
        val getCategory = NetworkConfig().getConnectionHainaBearer(context).removeUserBanned(idForum,idUser)
        getCategory.enqueue(object : retrofit2.Callback<ResponseRemoveUserBan>{
            override fun onResponse(call: Call<ResponseRemoveUserBan>, response: Response<ResponseRemoveUserBan>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAbout(response.body()?.message.toString())
                    val data = response.body()?.data!!
                    view.getRemoveBanned(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAbout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseRemoveUserBan>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAbout(t.localizedMessage.toString())
            }
        })
    }

}