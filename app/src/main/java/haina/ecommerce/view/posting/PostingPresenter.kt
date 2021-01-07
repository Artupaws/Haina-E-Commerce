package haina.ecommerce.view.posting

import android.content.Context
import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCheckRegisterCompany
import retrofit2.Call
import retrofit2.Response

class PostingPresenter(val view:PostingContract, val context: Context) {

    fun checkRegisterCompany(){
        val callCheckRegisterCompany = NetworkConfig().getConnectionHainaBearer(context).checkRegisterCompany()
            callCheckRegisterCompany.enqueue(object : retrofit2.Callback<ResponseCheckRegisterCompany>{
                override fun onResponse(call: Call<ResponseCheckRegisterCompany>, response: Response<ResponseCheckRegisterCompany>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        view.checkRegisterCompanyTrue("Company Registered", response.body()?.dataCompany)
                    } else {
                        view.checkRegisterCompanyTrue("Company Unregistered", response.body()?.dataCompany)
                    }
                }
                override fun onFailure(call: Call<ResponseCheckRegisterCompany>, t: Throwable) {
                    Log.d("failure", t.message)
                    view.checkRegisterCompanyFalse(t.localizedMessage)
                }

            })
    }

}