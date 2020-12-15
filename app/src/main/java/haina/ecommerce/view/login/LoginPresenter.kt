package haina.ecommerce.view.login

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseLogin
import retrofit2.Call
import retrofit2.Response

class LoginPresenter (val view: LoginContract){

    fun loginUser(email:String, password:String, deviceToken:String){
        NetworkConfig().getConnectionHaina().loginUser(email, password, deviceToken)
                .enqueue(object : retrofit2.Callback<ResponseLogin>{
                    override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            view.getToken(response.body()?.data?.token.toString())
                            view.successLogin(response.body()?.message.toString())
                        } else {
                            view.failedLogin(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        view.failedLogin(t.localizedMessage)
                    }

                })
    }

}