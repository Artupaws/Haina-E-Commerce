package haina.ecommerce.view.flight.chooseairline

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.flight.ResponseGetListAirline
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ChooseAirlineFirstPresenter(val view: ChooseAirlineFirstContract, val context:Context) {

    fun getAirlinesData(tripType:String, origin:String, destination:String, departDate:String, returnDate:String?,
                        adult:Int, child:Int, baby:Int, aAC:String){
        val getAirline = NetworkConfig().getConnectionHainaBearer(context).getListAirlines(tripType, origin, destination, departDate, returnDate, adult, child, baby, aAC)
        getAirline.enqueue(object : retrofit2.Callback<ResponseGetListAirline>{
            override fun onResponse(call: Call<ResponseGetListAirline>, response: Response<ResponseGetListAirline>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageChooseAirline(response.body()?.message.toString())
                    val data = response.body()?.dataAirline
                    view.getDataAirline(data)
                } else {
                    val dataError = JSONObject(response.errorBody()?.string())
                    view.accessCode(dataError.getString("data"))
                    view.messageChooseAirline(dataError.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListAirline>, t: Throwable) {
                view.messageChooseAirline(t.localizedMessage.toString())
            }

        })
    }

}