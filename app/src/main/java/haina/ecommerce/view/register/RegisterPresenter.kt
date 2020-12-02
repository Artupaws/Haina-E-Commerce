package haina.ecommerce.view.register

import android.content.Context
import haina.ecommerce.api.ApiRegisterUser
import haina.ecommerce.model.ResponseRegister
import haina.ecommerce.preference.SharedPreference
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RegisterPresenter (val view: RegisterContract){


    fun createUser(fullname: String, email: String, username:String, phone: String, password: String, apiKey:String, deviceToken:String){
        ApiRegisterUser().getInstance()
            .registerUser(fullname, email, username, phone, password, apiKey, deviceToken)
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