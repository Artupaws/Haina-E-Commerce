package haina.ecommerce.view.flight.detailfilldata

import android.content.Context
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.flight.ResponseGetListNationality
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailFillDataPresenter(val view: DetailFillDataContract, val context: Context) {

    fun getListCountry(){
        val getListCountry = NetworkConfig().getConnectionHainaBearer(context).getListNationality()
        getListCountry.enqueue(object : retrofit2.Callback<ResponseGetListNationality>{
            override fun onResponse(call: Call<ResponseGetListNationality>, response: Response<ResponseGetListNationality>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetCountryList(response.body()?.message.toString())
                    val data = response.body()?.dataNationality
                    view.getListCountry(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetCountryList(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListNationality>, t: Throwable) {
                view.messageGetCountryList(t.localizedMessage.toString())
            }

        })
    }

}