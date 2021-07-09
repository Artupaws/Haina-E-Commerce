package haina.ecommerce.view.datacompany.address

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddAddressCompany
import haina.ecommerce.model.ResponseListJobLocation
import retrofit2.Call
import retrofit2.Response

class AddressCompanyPresenter(val view:AddressCompanyContract, val context: Context) {

    fun getListLocation(){
        val getListLocation = NetworkConfig().getConnectionHaina().getDataListJobLocation()
        getListLocation.enqueue(object : retrofit2.Callback<ResponseListJobLocation>{
            override fun onResponse(call: Call<ResponseListJobLocation>, response: Response<ResponseListJobLocation>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListLocation(data)
                    view.messageGetListLocation(response.body()?.message.toString())
                } else {
                    view.messageGetListLocation(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.messageGetListLocation(t.localizedMessage.toString())
            }

        })
    }

    fun addAddressCompany(idCompany:String, address:String, idCity:String){
        val addAddressCompany = NetworkConfig().getConnectionHainaBearer(context).addAddressCompany(idCompany, address, idCity)
        addAddressCompany.enqueue(object : retrofit2.Callback<ResponseAddAddressCompany>{
            override fun onResponse(call: Call<ResponseAddAddressCompany>, response: Response<ResponseAddAddressCompany>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddressCompany("1")
                } else {
                    view.messageAddressCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseAddAddressCompany>, t: Throwable) {
                view.messageAddressCompany(t.localizedMessage.toString())
            }

        })
    }

}