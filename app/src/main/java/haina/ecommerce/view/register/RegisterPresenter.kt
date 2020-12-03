package haina.ecommerce.view.register

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseRegister
import retrofit2.Call
import retrofit2.Response

class RegisterPresenter (val view: RegisterContract){

    fun createUser(fullname: String, email: String, username:String, phone: String, password: String, apiKey:String, deviceToken:String){
        NetworkConfig().registerUser().createUser(fullname, email, username, phone, password, apiKey, deviceToken)
            .enqueue(object : retrofit2.Callback<ResponseRegister>{
                override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        view.successCreateUser(response.body()?.message.toString())
                    } else {
                        view.successCreateUser(response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    view.errorCreateUser(t.localizedMessage)
                }

            })
    }

}