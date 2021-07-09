package haina.ecommerce.view.other

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.service.ResponseGetService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class OtherPresenter(val view: OtherContract, val context: Context) {

    fun getListService(){
        val getListService = NetworkConfig().getConnectionHainaBearer(context).getListService()
        getListService.enqueue(object : retrofit2.Callback<ResponseGetService>{
            override fun onResponse(call: Call<ResponseGetService>, response: Response<ResponseGetService>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListService(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListService(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetListService(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetService>, t: Throwable) {
                view.messageGetListService(t.localizedMessage.toString())
            }

        })
    }

}