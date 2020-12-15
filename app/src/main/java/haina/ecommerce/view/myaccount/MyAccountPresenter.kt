package haina.ecommerce.view.myaccount

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseChangeImageProfile
import haina.ecommerce.model.ResponseGetDataUser
import haina.ecommerce.model.ResponseLogout
import haina.ecommerce.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class MyAccountPresenter (val view: MyAccountContract, val context: Context){

    fun getDataUserProfile(){
        NetworkConfig().getConnectionHainaBearer(context).getDataUser(Constants.APIKEY)
                .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                    override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            val data = response.body()?.data
                            view.getDataUser(data)
                            view.successGetDataUser(response.body()?.message.toString())
                        } else {
                            view.errorGetDataUSer(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                        view.errorGetDataUSer(t.localizedMessage)
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
                            view.successLogout(response.body()?.message.toString())
                        } else {
                            view.errorLogout(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                        view.errorLogout(t.localizedMessage)
                    }

                })
    }

    fun changeImageProfile(apiKey:RequestBody, file:MultipartBody.Part){
        val callChangeImageProfile = NetworkConfig().getConnectionHainaBearer(context).changeImageProfile(apiKey,file)
        callChangeImageProfile.enqueue(object : retrofit2.Callback<ResponseChangeImageProfile>{
            override fun onResponse(
                call: Call<ResponseChangeImageProfile>,
                response: Response<ResponseChangeImageProfile>
            ) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.successChangeImageProfile(response.body()?.message.toString())
                } else {
                    view.errorChangeImageProfile(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseChangeImageProfile>, t: Throwable) {
                view.errorChangeImageProfile(t.localizedMessage)
            }

        })
    }
}
