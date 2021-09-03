package haina.ecommerce.view.flight.setbooking

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.flight.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SetBookingPresenter(val view:SetBookingContract, val context: Context) {

    fun getCalculationTicketPrice(body:RequestPrice){
        val getCalculation = NetworkConfig().getConnectionHainaBearer(context).getCalculationTicketPrice(body)
        getCalculation.enqueue(object : retrofit2.Callback<ResponseGetRealTicketPrice>{
            override fun onResponse(call: Call<ResponseGetRealTicketPrice>, response: Response<ResponseGetRealTicketPrice>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCalculationPrice(response.body()?.message.toString())
                    val data = response.body()?.dataRealTicketPrice
                    view.getCalculationPrice(data)

                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.accessCode(error.getString("data"))
                    view.messageCalculationPrice(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetRealTicketPrice>, t: Throwable) {
                view.messageCalculationPrice(t.localizedMessage.toString())
            }

        })
    }

    fun setDataPassenger(body: RequestSetPassenger){
        val setDataPassenger = NetworkConfig().getConnectionHainaBearer(context).setDataPassenger(body)
        setDataPassenger.enqueue(object : retrofit2.Callback<ResponseSetDataPassenger>{
            override fun onResponse(call: Call<ResponseSetDataPassenger>, response: Response<ResponseSetDataPassenger>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageSetDataPassenger(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getIdSetPassenger(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageSetDataPassenger(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseSetDataPassenger>, t: Throwable) {
                view.messageSetDataPassenger(t.localizedMessage.toString() )
            }

        })
    }


}