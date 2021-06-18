package haina.ecommerce.view.login

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseLogin
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class LoginPresenter (val view: LoginContract){

    fun loginUser(email:String, password:String, deviceToken:String, deviceName:String){
        NetworkConfig().getConnectionHaina().loginUser(email, password, deviceToken, deviceName)
                .enqueue(object : retrofit2.Callback<ResponseLogin>{
                    override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            view.getToken(response.body()?.data!!)
                            view.successLogin(response.body()?.message.toString())
                        } else {
                            val errorResponse = JSONObject(response.errorBody()?.string()!!)
                            view.failedLogin(errorResponse.getString("message"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        view.failedLogin(t.localizedMessage)
                    }
                })
    }
    fun loginWithGoogle(firebaseToken:String?, deviceToken:String){
        NetworkConfig().getConnectionHaina().loginGoogle(firebaseToken,deviceToken)
            .enqueue(object : retrofit2.Callback<ResponseLogin>{
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful ){
                        if (response.body()?.value == 1){
                            if(response.body()?.message=="Please Continue Registration!"){
                                view.loginRegistration()
                            }else{
                                view.getToken(response.body()?.data!!)
                                view.successLogin(response.body()?.message.toString())
                            }
                        }
                    } else {
                        val errorResponse = JSONObject(response.errorBody()?.string()!!)
                        view.failedLogin(errorResponse.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    view.failedLogin(t.localizedMessage)
                }
            })
    }

}