package haina.ecommerce.view.splashscreen

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetDataUser
import haina.ecommerce.model.ResponseLogout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SplashScreenPresenter(val view: SplashScreenContract.View, val context: Context) {

    fun getServerStatus(){
        NetworkConfig().getConnectionHainaBearer(context).getServerStatus()
            .enqueue(object : retrofit2.Callback<ResponseLogout>{
                override fun onResponse(call: Call<ResponseLogout>, response: Response<ResponseLogout>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        val data = response.body()?.message!!
                        view.serverOnline(data)
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.serverOffline(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                    view.serverOffline(t.localizedMessage)
                }

            })
    }
}