package haina.ecommerce.view.register.company

import android.content.Context
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseRegisterCompany
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class RegisterCompanyPresenter(val view: RegisterCompanyContract, val context: Context) {

    fun registerCompany(imageCompany:MultipartBody.Part, name: RequestBody, description: RequestBody){
        val callRegisterCompany = NetworkConfig().getConnectionHainaBearer(context).registerCompany(imageCompany, name, description)
        callRegisterCompany.enqueue(object : retrofit2.Callback<ResponseRegisterCompany>{
            override fun onResponse(call: Call<ResponseRegisterCompany>, response: Response<ResponseRegisterCompany>) {
                if(response.isSuccessful && response.body()?.value == 1)
                    view.messageRegisterCompany("1")
                else
                    view.messageRegisterCompany("0")
            }

            override fun onFailure(call: Call<ResponseRegisterCompany>, t: Throwable) {
                view.messageRegisterCompany(t.localizedMessage.toString())
            }

        })
    }

}