package haina.ecommerce.view.myaccount

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseChangeImageProfile
import haina.ecommerce.model.ResponseCheckRegisterCompany
import haina.ecommerce.model.ResponseGetDataUser
import haina.ecommerce.model.ResponseLogout
import haina.ecommerce.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class MyAccountPresenter (val view: MyAccountContract.View, val context: Context){

    fun getDataUserProfile(){
        NetworkConfig().getConnectionHainaBearer(context).getDataUser()
                .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                    override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            val data = response.body()?.data
                            view.getDataUser(data)
                            view.messageGetDataUser(response.body()?.message.toString())
                        } else {
                            val error = JSONObject(response.errorBody()?.string()!!)
                            view.messageGetDataUser(error.getString("message"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                        view.messageGetDataUser(t.localizedMessage)
                    }

                })
    }

    fun resetTokenUser(){
        NetworkConfig().getConnectionHainaBearer(context).userLogout(Constants.APIKEY)
                .enqueue(object : retrofit2.Callback<ResponseLogout>{
                    override fun onResponse(call: Call<ResponseLogout>, response: Response<ResponseLogout>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            val data = response.body()?.data
                            view.resetTokenUser(data)
                            view.messageLogout(response.body()?.message.toString())
                        } else {
                            val error = JSONObject(response.errorBody()?.string()!!)
                            view.messageLogout(error.getString("message"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                        view.messageLogout(t.localizedMessage)
                    }

                })
    }

    fun changeImageProfile(file:MultipartBody.Part){
        val callChangeImageProfile = NetworkConfig().getConnectionHainaBearer(context).changeImageProfile(file)
        callChangeImageProfile.enqueue(object : retrofit2.Callback<ResponseChangeImageProfile>{
            override fun onResponse(
                call: Call<ResponseChangeImageProfile>,
                response: Response<ResponseChangeImageProfile>
            ) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageChangeImageProfile(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageChangeImageProfile(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseChangeImageProfile>, t: Throwable) {
                view.messageChangeImageProfile("Failed")
            }

        })
    }

    fun checkDataCompany(){
        val callCheckDataCompany = NetworkConfig().getConnectionHainaBearer(context).checkRegisterCompany()
        callCheckDataCompany.enqueue(object : retrofit2.Callback<ResponseCheckRegisterCompany>{
            override fun onResponse(call: Call<ResponseCheckRegisterCompany>, response: Response<ResponseCheckRegisterCompany>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCheckDataCompany("Company Registered")
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCheckDataCompany(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCheckRegisterCompany>, t: Throwable) {
                view.messageCheckDataCompany(t.localizedMessage.toString())
            }

        })
    }
}
