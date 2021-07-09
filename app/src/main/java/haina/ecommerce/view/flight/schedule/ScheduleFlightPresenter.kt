package haina.ecommerce.view.flight.schedule

import android.content.Context
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.flight.ResponseGetListAirline
import haina.ecommerce.model.flight.ResponseGetListAirport
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ScheduleFlightPresenter(val view: ScheduleContract.View, val context: Context) {

    fun getDataAirport(){
        view.showLoading()
        val getAirport = NetworkConfig().getConnectionHainaBearer(context).getAirport()
        getAirport.enqueue(object : retrofit2.Callback<ResponseGetListAirport>{
            override fun onResponse(call: Call<ResponseGetListAirport>, response: Response<ResponseGetListAirport>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAirport(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataAirport(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAirport(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListAirport>, t: Throwable) {
                view.dismissLoading()
                view.messageGetAirport(t.localizedMessage.toString())
            }

        })
    }

}