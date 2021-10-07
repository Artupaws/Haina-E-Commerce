package haina.ecommerce.view.register.account

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseRegister
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RegisterPresenter (val view: RegisterContract){

    fun createUser(fullname: String, email: String, username:String, phone: String, password: String, deviceToken:String, deviceName:String){
        NetworkConfig().getConnectionHaina().createUser(fullname, email, username, phone, password, deviceToken, deviceName)
            .enqueue(object : retrofit2.Callback<ResponseRegister>{
                override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                    if (response.isSuccessful && response.body()?.value == 1){

                        view.getTokenUser(response.body()?.data?.token.toString())
                        view.successCreateUser(response.body()?.message.toString())
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.errorCreateUser(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    view.errorCreateUser("anjay")
                }
            })
    }
}
