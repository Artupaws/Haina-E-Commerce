package haina.ecommerce.view.myaccount.changepassword

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseChangePassword
import haina.ecommerce.model.ResponseLogout
import haina.ecommerce.util.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ChangePasswordPresenter(val view:ChangePasswordContract, val context: Context) {

    fun changePasswordUser(currentPassword:String, newPassword:String){
        val changePasswordUser = NetworkConfig().getConnectionHainaBearer(context).changePassword(currentPassword, newPassword)
        changePasswordUser.enqueue(object : retrofit2.Callback<ResponseChangePassword>{
            override fun onResponse(call: Call<ResponseChangePassword>, response: Response<ResponseChangePassword>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageChangePassword(response.body()?.message!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageChangePassword(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseChangePassword>, t: Throwable) {
                view.messageChangePassword(t.localizedMessage.toString())
            }
        })
    }

    fun resetTokenUser(){
        NetworkConfig().getConnectionHainaBearer(context).userLogout(Constants.APIKEY)
            .enqueue(object : retrofit2.Callback<ResponseLogout>{
                override fun onResponse(call: Call<ResponseLogout>, response: Response<ResponseLogout>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        view.messageLogout(response.body()?.message.toString())
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.messageLogout(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                    view.messageLogout(t.localizedMessage.toString())
                }

            })
    }

}