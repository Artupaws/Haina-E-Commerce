package haina.ecommerce.view.datacompany

import android.content.Context
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCheckRegisterCompany
import haina.ecommerce.model.ResponseListJobLocation
import retrofit2.Call
import retrofit2.Response

class DataCompanyPresenter(val view: DataCompanyContract, val context: Context) {

    fun getDataCompany(){
        val callGetDataCompany = NetworkConfig().getConnectionHainaBearer(context).getDataCompany()
        callGetDataCompany.enqueue(object : retrofit2.Callback<ResponseCheckRegisterCompany>{
            override fun onResponse(call: Call<ResponseCheckRegisterCompany>, response: Response<ResponseCheckRegisterCompany>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataCompany
                    view.getDataCompany(data!!)
                    view.messageGetDataCompany(response.body()?.message.toString())
                } else {
                    view.messageGetDataCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCheckRegisterCompany>, t: Throwable) {
                view.messageGetDataCompany(t.localizedMessage.toString())
            }

        })
    }

    fun loadListLocation(){
        val loadListLocation = NetworkConfig().getConnectionHaina().getDataListJobLocation()
        loadListLocation.enqueue(object : retrofit2.Callback<ResponseListJobLocation>{
            override fun onResponse(call: Call<ResponseListJobLocation>, response: Response<ResponseListJobLocation>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.loadJobLocation(data)
                    view.messageGetDataCompany(response.body()?.message.toString())
                } else {
                    view.messageGetDataCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.messageGetDataCompany(t.localizedMessage.toString())
            }

        })
    }

}